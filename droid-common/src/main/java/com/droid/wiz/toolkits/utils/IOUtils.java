package com.droid.wiz.toolkits.utils;

import java.io.Closeable;
import java.io.IOException;

import androidx.annotation.Nullable;

/**
 * Created by liuXiao on 10/19/2021
 */
public class IOUtils {

  public static void close(@Nullable Closeable closeable) {
    if (closeable == null) {
      return;
    }
    try {
      closeable.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
