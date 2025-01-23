package com.droid.wiz.toolkits.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

public class AlbumUtils {

  private static final String ALBUM_APP_NAME = "TennCredit";
  private static final String PATH_ALBUM =
    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
      .getAbsolutePath() + File.separator + ALBUM_APP_NAME + File.separator;

  static {
    if (!com.droid.wiz.toolkits.utils.FileUtils.checkFileExists(PATH_ALBUM)) {
      File file = new File(PATH_ALBUM);
      file.mkdirs();
    }
  }

  public static String getAlbumDirRelative() {
    return Environment.DIRECTORY_PICTURES + File.separator + ALBUM_APP_NAME + File.separator;
  }

  public static String getAlbumDirAbsolute() {
    return PATH_ALBUM;
  }

  public static String savePicture(Context context, Bitmap bitmap) {
    return Build.VERSION.SDK_INT < Build.VERSION_CODES.Q ? savePictureUnderApi29(context, bitmap) :
      savePictureOverApi29(context, bitmap);
  }

  private static String savePictureUnderApi29(Context context, Bitmap bitmap) {
    String path = AlbumUtils.getAlbumDirAbsolute();
    File fileFold = new File(path);
    if (!fileFold.exists()) {
      fileFold.mkdirs();
    }
    String fileName = path + System.currentTimeMillis() + ".png";
    File file = new File(fileName);
    if (file.exists()) {
      file.delete();
    }
    FileOutputStream out;
    try {
      out = new FileOutputStream(file);
      if (bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)) {
        out.flush();
        out.close();
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
        return fileName;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private static String savePictureOverApi29(Context context, Bitmap bitmap) {
    String fileName = System.currentTimeMillis() + ".png";
    Uri contentUri;
    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
      contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    } else {
      contentUri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
    }
    ContentValues contentValues = new ContentValues();
    contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
    contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/*");
    contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, AlbumUtils.getAlbumDirRelative());
    contentValues.put(MediaStore.MediaColumns.IS_PENDING, 1);
    Uri uri = context.getContentResolver().insert(contentUri, contentValues);
    if (uri == null) {
      return null;
    }
    OutputStream os = null;
    try {
      os = context.getContentResolver().openOutputStream(uri);
      bitmap.compress(Bitmap.CompressFormat.PNG, 90, os);
      contentValues.clear();
      contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0);
      context.getContentResolver().update(uri, contentValues, null, null);
      return AlbumUtils.getAlbumDirAbsolute().concat(fileName);
    } catch (Exception e) {
      e.printStackTrace();
      context.getContentResolver().delete(uri, null, null);
    } finally {
      com.droid.wiz.toolkits.utils.FileUtils.closeIO(os);
    }
    return null;
  }

  public static String getPictureRelativePath(String filePath){
    if (FileUtils.checkFileExists(filePath)){
      if (filePath.contains("emulated/0")){
        String[] s = filePath.split("emulated/0");
        if (s.length == 2){
          return filePath.split("emulated/0")[1];
        }
      }
    }
    return filePath;
  }
}
