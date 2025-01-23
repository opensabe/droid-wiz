package com.droid.wiz.toolkits.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

/**
 * Created by fangzheng on 11/04/2020
 */
public class AppInstallUtils {
  public static boolean hasInstall(Context context, String packageName) {
    if (TextUtils.isEmpty(packageName)) {
      return false;
    }
    try {
      ApplicationInfo info = context
          .getPackageManager()
          .getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
      return true;
    } catch (PackageManager.NameNotFoundException e) {
      return false;
    }
  }
}
