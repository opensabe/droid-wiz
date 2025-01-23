package com.droid.wiz.toolkits.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class JsonCacheUtils {

  public static final String DIR_NAME;

  static {
    DIR_NAME = Tools.getApplication().getCacheDir().getAbsolutePath() + File.separator + "json";
  }

  public static void cache(@NonNull String fileName, @Nullable String json) {
    if (TextUtils.isEmpty(json)) {
      return;
    }
    BufferedWriter writer = null;
    try {
      File dir = new File(DIR_NAME);
      if (!dir.exists() && !dir.mkdirs()) {
        return;
      }
      File file = new File(dir, fileName);
      if (file.exists() && !file.delete()) {
        return;
      }
      writer = new BufferedWriter(new FileWriter(file));
      writer.write(json);
      writer.flush();
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    } finally {
      closeIo(writer);
    }
  }

  @NonNull
  public static String getCache(@NonNull String fileName) {
    BufferedReader reader = null;
    try {
      File dir = new File(DIR_NAME);
      if (!dir.exists()) {
        return "";
      }
      reader = new BufferedReader(new FileReader(new File(dir, fileName)));
      StringBuilder stringBuilder = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        stringBuilder.append(line);
      }
      return stringBuilder.toString();
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    } finally {
      closeIo(reader);
    }
    return "";
  }

  private static void closeIo(Closeable... closeables) {
    if (closeables != null) {
      for (Closeable closeable : closeables) {
        if (closeable != null) {
          try {
            closeable.close();
          } catch (Throwable ignored) {
          }
        }
      }
    }
  }
}
