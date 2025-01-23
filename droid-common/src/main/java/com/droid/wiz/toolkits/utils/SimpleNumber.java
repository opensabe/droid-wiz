package com.droid.wiz.toolkits.utils;


import androidx.annotation.NonNull;

@SuppressWarnings("unused")
public class SimpleNumber {

  public static int compare(String x, String y) {
    return compare(x, y, false);
  }

  //thousandSeparator
  public static int compare(String x, String y, boolean hasThousandSeparator) {
    if (com.droid.wiz.toolkits.utils.Tools.isEmpty(x) || com.droid.wiz.toolkits.utils.Tools.isEmpty(y)) {
      return 0;
    }
    if (hasThousandSeparator) {
      x = replace(x);
      y = replace(y);
    }

    try {
      long lX = (long) (Double.parseDouble(x) * 10000);
      long lY = (long) (Double.parseDouble(y) * 10000);
      return Long.compare(lX, lY);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

  public static boolean isLessThanZero(String x) {
    return isLessThanZero(x, false);
  }

  public static boolean isLessThanZero(String x, boolean hasThousandSeparator) {
    if (com.droid.wiz.toolkits.utils.Tools.isEmpty(x)) {
      return false;
    }
    if (hasThousandSeparator) {
      x = replace(x);
    }
    try {
      return Double.compare(Double.parseDouble(x), 0) < 0;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public static boolean isGreaterThanZero(String x) {
    return isGreaterThanZero(x, false);
  }

  public static boolean isGreaterThanZero(String x, boolean hasThousandSeparator) {
    if (com.droid.wiz.toolkits.utils.Tools.isEmpty(x)) {
      return false;
    }
    if (hasThousandSeparator) {
      x = replace(x);
    }
    try {
      return Double.compare(Double.parseDouble(x), 0) > 0;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public static boolean isEqualsZero(String x) {
    return isEqualsZero(x, false);
  }

  public static boolean isEqualsZero(String x, boolean hasThousandSeparator) {
    if (Tools.isEmpty(x)) {
      return false;
    }
    if (hasThousandSeparator) {
      x = replace(x);
    }
    try {
      return Double.compare(Double.parseDouble(x), 0) == 0;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  private static String replace(@NonNull String number) {
    if (number.contains(",")) {
      return number.replaceAll(",", "");
    }
    return number;
  }
}
