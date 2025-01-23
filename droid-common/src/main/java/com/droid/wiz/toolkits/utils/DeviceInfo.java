package com.droid.wiz.toolkits.utils;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * @author tianli
 */
public class DeviceInfo {

  private String deviceId;
  private static DeviceInfo instance = null;
  private final ReentrantLock installationIdLock = new ReentrantLock();

  public static DeviceInfo getInstance() {
    if (instance == null) {
      instance = new DeviceInfo();
    }
    return instance;
  }

  public String getDeviceId() {
    if (!TextUtils.isEmpty(deviceId)) {
      return deviceId;
    }
    SharedPreferences prefs =
        SharedPreferencesWrapper.getSharedPreferences("com.tenn.credit.app.prefs");
    deviceId = prefs.getString("installation.id", null);
    if (deviceId == null) {
      deviceId = createInstallationUUID(prefs);
    }
    return deviceId;
  }

  @SuppressLint("ApplySharedPref")
  private String createInstallationUUID(SharedPreferences prefs) {
    this.installationIdLock.lock();
    try {
      String uuid = prefs.getString("installation.id", null);
      if (uuid == null) {
        uuid = getDeviceIdOld();
        prefs.edit().putString("installation.id", uuid).apply();
      }
      return uuid;
    } finally {
      this.installationIdLock.unlock();
    }
  }

  @SuppressLint("HardwareIds")
  private String getDeviceIdOld() {
    return UUID.randomUUID().toString();
  }
}
