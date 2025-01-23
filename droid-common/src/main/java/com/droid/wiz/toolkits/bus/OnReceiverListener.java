package com.droid.wiz.toolkits.bus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by liuXiao on 09/28/2021
 */
public interface OnReceiverListener {
  void onReceiver(@NonNull String code, @Nullable Object data);
}
