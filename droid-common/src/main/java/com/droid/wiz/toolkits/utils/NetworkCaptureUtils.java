package com.droid.wiz.toolkits.utils;

import androidx.annotation.Nullable;
import okhttp3.Interceptor;

public class NetworkCaptureUtils {

  @Nullable
  public static Interceptor getNetworkCaptureInterceptor() {
    try {
      Class<?> clazz =
          Class.forName("com.liuxiao352.networkcapturecore.interceptor.NetworkCaptureInterceptor");
      return (Interceptor) clazz.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      return null;
    }
  }
}
