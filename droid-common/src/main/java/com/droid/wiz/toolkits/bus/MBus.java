package com.droid.wiz.toolkits.bus;

import java.util.HashSet;
import java.util.Set;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by liuXiao on 09/27/2021
 */
public class MBus {

  private volatile static MBus sIns;
  private Set<MReceiver> mReceivers;
  private Handler mMainHandler;

  public static MBus getIns() {
    if (sIns == null) {
      synchronized (MBus.class) {
        if (sIns == null) {
          sIns = new MBus();
        }
      }
    }
    return sIns;
  }

  private MBus() {
  }

  public void registerReceiver(MReceiver receiver) {
    if (mReceivers == null) {
      mReceivers = new HashSet<>();
    }
    mReceivers.add(receiver);
  }

  public void unregisterReceiver(MReceiver receiver) {
    if (mReceivers == null) {
      return;
    }
    mReceivers.remove(receiver);
  }

  public void sendMsg(@NonNull String code, @Nullable Object data, @Nullable MReceiver ignoreReceiver) {
    if (mReceivers == null) {
      return;
    }
    for (MReceiver receiver : mReceivers) {
      if (receiver == ignoreReceiver) {
        continue;
      }
      if (receiver.codes.contains(code)) {
        if (isMainThread()) {
          receiver.onReceiver(code, data);
        } else {
          getMainHandler().post(() -> receiver.onReceiver(code, data));
        }
      }
    }
  }

  public boolean isMainThread() {
    return Looper.getMainLooper() == Looper.myLooper();
  }

  public Handler getMainHandler() {
    if (mMainHandler == null) {
      mMainHandler = new Handler(Looper.getMainLooper());
    }
    return mMainHandler;
  }

  public static void send(@NonNull String code, @Nullable Object data,
      @Nullable MReceiver ignoreReceiver) {
    getIns().sendMsg(code, data, ignoreReceiver);
  }

  public static void send(@NonNull String code, MReceiver ignoreReceiver) {
    send(code, null, ignoreReceiver);
  }

  public static void send(@NonNull String code, @Nullable Object data) {
    send(code, data, null);
  }

  public static void send(@NonNull String code) {
    send(code, null, null);
  }

  public static void register(MReceiver receiver) {
    getIns().registerReceiver(receiver);
  }

  public static void unregister(MReceiver receiver) {
    getIns().unregisterReceiver(receiver);
  }
}
