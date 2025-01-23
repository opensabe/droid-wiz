package com.droid.wiz.toolkits.utils;

/**
 * Created by fangzheng on 2021/10/25.
 */
public class TimestampUtils {

  /**
   * 根据自定义的方法产生新的数字
   *
   * @param timestamp long
   * @return long
   */
  public static long generate(long timestamp) {
    return Long.rotateLeft((timestamp << 1) + 2, 1);
  }
}
