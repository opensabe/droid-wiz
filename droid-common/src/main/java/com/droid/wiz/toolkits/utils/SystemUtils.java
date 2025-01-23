package com.droid.wiz.toolkits.utils;

import java.util.Locale;

import android.os.Build;

public class SystemUtils {

  /**
   * 获取当前手机系统语言
   *
   * @return 返回当前系统语言。例如：当前设置的是"中文-中国",则返回"zh-CN"
   */
  public static String getSystemLanguage() {
    try {
      return Locale.getDefault().getLanguage();
    } catch (Throwable t) {
    }
    return "unknown";
  }

  /**
   * 获取当前系统上的语言列表(Locale列表)
   *
   * @return 语言列表
   */
  public static Locale[] getSystemLanguageList() {
    return Locale.getAvailableLocales();
  }

  /**
   * 获取当前手机系统版本号
   *
   * @return 系统版本号
   */
  public static String getSystemVersion() {
    try {
      return Build.VERSION.RELEASE;
    } catch (Throwable t) {
    }
    return "unknown";
  }

  /**
   * 获取手机型号
   *
   * @return 手机型号
   */
  public static String getSystemModel() {
    try {
      return Build.MODEL;
    } catch (Throwable t) {
    }
    return "unknown";
  }

  /**
   * 获取手机厂商
   *
   * @return 手机厂商
   */
  public static String getDeviceBrand() {
    try {
      return Build.BRAND;
    } catch (Throwable t) {
    }
    return "unknown";
  }

  //获取主要的CPU架构
  public static String getCpuArchitecture() {
    if (Build.SUPPORTED_ABIS.length > 0) {
      return Build.SUPPORTED_ABIS[0];
    }
    return Build.CPU_ABI;
  }
}
