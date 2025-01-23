package com.droid.wiz.toolkits.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

public class ThreadUtils {

  private static Handler sMainHandler;

  private static ExecutorService mCachedThreadPool;

  public static void executeMainThread(@NonNull final Runnable runnable) {
    if (sMainHandler == null) {
      sMainHandler = new Handler(Looper.getMainLooper());
    }
    sMainHandler.post(runnable);
  }

  public static void executeWorkThread(@NonNull final Runnable runnable) {
    if (mCachedThreadPool == null) {
      mCachedThreadPool = Executors.newCachedThreadPool();
    }
    mCachedThreadPool.execute(runnable);
  }
}
