package com.droid.wiz.toolkits.utils;

import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;

public class AppUtils {

  public static void restartApp() {
    try {
      Application application = Tools.getApplication();
      PackageManager packageManager = application.getPackageManager();
      if (null == packageManager) {
        return;
      }
      final Intent intent = packageManager.getLaunchIntentForPackage(application.getPackageName());
      if (intent != null) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        application.startActivity(intent);
      }
      android.os.Process.killProcess(android.os.Process.myPid());
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
