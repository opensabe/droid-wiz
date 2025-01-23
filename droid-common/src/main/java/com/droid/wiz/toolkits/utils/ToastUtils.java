package com.droid.wiz.toolkits.utils;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.droid.wiz.toolkits.R;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import me.drakeet.support.toast.ToastCompat;

/**
 * Created by liuXiao on 06/29/2021
 */
public class ToastUtils {

  private static ToastCompat sToast;

  public static void show(@Nullable String msg) {
    if (TextUtils.isEmpty(msg)) {
      return;
    }
    if (sToast != null) {
      sToast.getBaseToast().cancel();
    }
    try {
      sToast = ToastCompat.makeText(com.droid.wiz.toolkits.utils.Tools.getApplication(), msg, Toast.LENGTH_SHORT);
      View view = View.inflate(com.droid.wiz.toolkits.utils.Tools.getApplication(), R.layout.toast, null);
      TextView tvText = view.findViewById(R.id.tv_text);
      tvText.setMaxWidth(com.droid.wiz.toolkits.utils.Tools.dp2px(230));
      tvText.setText(msg);
      sToast.setView(view);
      sToast.setGravity(Gravity.CENTER, 0, 0);
      sToast.show();
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }

  public static void show(@StringRes int redId) {
    show(Tools.getString(redId));
  }
}
