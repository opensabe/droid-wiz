package com.droid.wiz.toolkits.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;


public final class FileUtils {

  public static boolean writeFile(InputStream inputStream, File file) {
    File destParentFile = file.getParentFile();
    if (!destParentFile.exists()) {
      if (!destParentFile.mkdirs()) {
        return false;
      }
    }
    BufferedInputStream bufferedInputStream = null;
    BufferedOutputStream bufferedOutputStream = null;
    boolean isSuccess = false;
    try {
      bufferedInputStream = new BufferedInputStream(inputStream);
      bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
      byte[] buffer = new byte[4096];
      while (true) {
        int read = bufferedInputStream.read(buffer);
        if (read < 0) {
          break;
        }
        bufferedOutputStream.write(buffer, 0, read);
      }
      bufferedOutputStream.flush();
      isSuccess = true;
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      com.droid.wiz.toolkits.utils.IOUtils.close(bufferedInputStream);
      com.droid.wiz.toolkits.utils.IOUtils.close(bufferedOutputStream);
    }
    return isSuccess;
  }

  public static boolean copy(String srcPath, String destPath) {
    File src = new File(srcPath);
    if (!src.exists()) {
      return false;
    }
    File destParentFile = new File(destPath).getParentFile();
    if (!destParentFile.exists()) {
      if (!destParentFile.mkdirs()) {
        return false;
      }
    }
    BufferedInputStream bufferedInputStream = null;
    BufferedOutputStream bufferedOutputStream = null;
    boolean isSuccess = false;
    try {
      bufferedInputStream = new BufferedInputStream(new FileInputStream(srcPath));
      bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(destPath));
      byte[] bytes = new byte[1024];
      int length;
      while ((length = bufferedInputStream.read(bytes)) != -1) {
        bufferedOutputStream.write(bytes, 0, length);
      }
      bufferedOutputStream.flush();
      isSuccess = true;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      com.droid.wiz.toolkits.utils.IOUtils.close(bufferedInputStream);
      IOUtils.close(bufferedOutputStream);
    }
    return isSuccess;
  }

  public static File uriToFileApiQ(Context context, Uri uri) {
    if (uri == null) {
      return null;
    }
    File file = null;
    try {
      //android10以上转换
      if (com.droid.wiz.toolkits.utils.Tools.equals(uri.getScheme(), ContentResolver.SCHEME_CONTENT)) {
        //把文件复制到沙盒目录
        ContentResolver contentResolver = context.getContentResolver();
        String displayName = System.currentTimeMillis() + "tenncredit." +
            MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(uri));
        InputStream inputStream = contentResolver.openInputStream(uri);
        if (inputStream != null) {
          File cache = new File(context.getCacheDir().getAbsolutePath(), displayName);
          FileOutputStream fos = new FileOutputStream(cache);
          writeFile(inputStream, cache);
          file = cache;
          fos.close();
          inputStream.close();
        }
      } else {
        file = new File(uri.getPath());
      }
      return file;
    } catch (Throwable e) {
      return null;
    }
  }

  public static String getRealFilePath(final Context context, final Uri uri) {
    if (uri == null) {
      return null;
    }
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
      File file = uriToFileApiQ(context, uri);
      if (file == null) {
        return null;
      }
      return file.getAbsolutePath();
    }
    final String scheme = uri.getScheme();
    String path = null;
    if (com.droid.wiz.toolkits.utils.Tools.isEmpty(scheme)) {
      path = uri.getPath();
    } else if (com.droid.wiz.toolkits.utils.Tools.equals(ContentResolver.SCHEME_FILE, scheme)) {
      path = uri.getPath();
    } else if (Tools.equals(ContentResolver.SCHEME_CONTENT, scheme)) {
      Cursor cursor = context.getContentResolver()
          .query(uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null);
      if (null != cursor) {
        if (cursor.moveToFirst()) {
          int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
          if (index > -1) {
            path = cursor.getString(index);
          }
        }
        cursor.close();
      }
    }
    return path;
  }

  public static boolean checkFileExists(String filePath) {
    return new File(filePath).exists();
  }

  public static void closeIO(Closeable closeable) {
    if (closeable != null) {
      try {
        closeable.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
