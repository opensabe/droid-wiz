package com.droid.wiz.toolkits.utils;

import androidx.annotation.IdRes;

/**
 * @author fangzheng
 *         判断view多次快速点击是否有效
 */

public class ClickUtil {

  /**
   * 两处点击的间隔时间(毫秒)
   */
  private static long sIntervalAtMillis = 1000L;
  /**
   * 资源id
   */
  private static int sResId;
  /**
   * 上次点击 时间戳
   */
  private static long sLastTime;

  private ClickUtil() {
  }

  /**
   * 是否合法
   *
   * @param resId 本次点击的view资源id
   * @return boolean
   */
  public static boolean isValid(@IdRes int resId) {
    //两处点击的间隔时间为1000(毫秒)
    return isValid(resId,sIntervalAtMillis);
  }

  public static boolean isValid(@IdRes int resId, long intervalAtMillis){
    long currentTime = System.currentTimeMillis();

    if (currentTime - sLastTime < intervalAtMillis && sResId == resId) {
      mLessThanOneHundred = currentTime - sLastTime < 100;
      return false;
    }

    sLastTime = currentTime;
    sResId = resId;
    mLessThanOneHundred = false;
    return true;
  }

  private static boolean mLessThanOneHundred;

  public static boolean isLessThanOneHundred(@IdRes int resId){
    return resId == sResId && mLessThanOneHundred;
  }
}
