package com.droid.wiz.toolkits.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class IntentUtils {

  public static final String GOOGLE_PLAY = "com.android.vending";
  private static final String GOOGLY_PLAY_URL = "https://play.google.com/store/apps/details?id=";


  public static void goGooglePlay(Context context) {
    try {
      if (!AppInstallUtils.hasInstall(com.droid.wiz.toolkits.utils.Tools.getContext(), GOOGLE_PLAY)) {
        com.droid.wiz.toolkits.utils.ToastUtils.show("Please install Google Play");
        return;
      }
      Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
      Intent intent = new Intent(Intent.ACTION_VIEW, uri);
      intent.setPackage(GOOGLE_PLAY);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(intent);
    } catch (Exception e) {
      if (!AppInstallUtils.hasInstall(com.droid.wiz.toolkits.utils.Tools.getContext(), GOOGLE_PLAY)) {
        ToastUtils.show("Please install Google Play");
      }
      e.printStackTrace();
    }
  }

  public static String getAppGooglePlayAddress() {
    return GOOGLY_PLAY_URL + Tools.getContext().getPackageName();
  }

}
