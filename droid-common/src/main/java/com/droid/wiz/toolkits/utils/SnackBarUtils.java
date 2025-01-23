package com.droid.wiz.toolkits.utils;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.view.Window;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;

public class SnackBarUtils {

  private static WeakReference<Activity> sCurrentActivityWeakReference;

  public static void setCurrentActivity(@Nullable WeakReference<Activity> activityWeakReference) {
    sCurrentActivityWeakReference = activityWeakReference;
  }

  public static void show(@Nullable String msg) {
    try {
      if (Tools.isEmpty(msg) || sCurrentActivityWeakReference == null ||
          sCurrentActivityWeakReference.get() == null ||
          sCurrentActivityWeakReference.get().isFinishing()) {
        return;
      }
      Snackbar snackbar =
          Snackbar.make(sCurrentActivityWeakReference.get().findViewById(Window.ID_ANDROID_CONTENT),
              msg, Snackbar.LENGTH_SHORT);
      snackbar.show();
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }
  }
}
