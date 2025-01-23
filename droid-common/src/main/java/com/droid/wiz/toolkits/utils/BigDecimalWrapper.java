package com.droid.wiz.toolkits.utils;

import java.math.BigDecimal;

/**
 * Created by fangzheng on 11/12/2019
 * <p>
 * BigDecimal包装类.
 * 避免null 以及特殊字符串包含逗号的数字
 */
public class BigDecimalWrapper {

  public static BigDecimal get(String number) {
    if (Tools.isEmpty(number)) {
      return new BigDecimal("0");
    }

    if (number.contains(",")) {
      number = number.replaceAll(",", "");
    }

    if (".".equals(number)) {
      return new BigDecimal("0");
    }

    if (!NumberUtils.isNumber(number)) {
      return new BigDecimal("0");
    }

    return new BigDecimal(number);
  }

  public static BigDecimal get(int number) {
    return new BigDecimal(number);
  }

  public static BigDecimal get(float number) {
    return get(number + "");
  }

  public static BigDecimal get(double number) {
    return get(number + "");
  }

  public static BigDecimal get(long number) {
    return get(number + "");
  }

  public static String getString(BigDecimal bigDecimal) {
    if (bigDecimal == null) {
      return "";
    }
    return bigDecimal.toString();
  }
}
