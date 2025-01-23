package com.droid.wiz.toolkits.net;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {

  public static String currentNetworkStatus(Context context) {

    try {
      ConnectivityManager connectivity =
          (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      if (connectivity == null) {
        return "offline";
      }
      NetworkInfo info = connectivity.getActiveNetworkInfo();
      if (info == null) {
        return "offline";
      }
      NetworkInfo netMobile = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
      if (netMobile != null && netMobile.getState() == NetworkInfo.State.CONNECTED) {
        return "mobile";
      }

      NetworkInfo netWifi = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
      if (netWifi != null && netWifi.getState() == NetworkInfo.State.CONNECTED) {
        return "wifi";
      }
    } catch (Throwable t) {
      return "unknown";
    }
    return "unknown";
  }

  public static boolean isNetWorkAvailable(Context context) {

    boolean isAvailable = false;

    try {
      if (context == null) {
        return false;
      }
      if (context instanceof Activity && ((Activity) context).isFinishing()) {
        return false;
      }

      ConnectivityManager manager =
          (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

      if (manager != null) {
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isAvailable()) {
          isAvailable = true;
        }
      }
    } catch (Throwable t) {
      return true;
    }
    return isAvailable;
  }

  public static boolean isMobileStatus(Context context) {
    return "mobile".equals(currentNetworkStatus(context));
  }
}
