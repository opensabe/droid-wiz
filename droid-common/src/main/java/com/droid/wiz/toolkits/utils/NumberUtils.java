package com.droid.wiz.toolkits.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Pattern;

import android.text.TextUtils;

/**
 * @author zn
 * description :
 * date : 2021/11/11
 */
public class NumberUtils {

  public static int getInt(String string) {
    int num = 0;
    try {
      if (com.droid.wiz.toolkits.utils.Tools.notEmpty(string)) {
        string = string.replaceAll(",", "");
        num = Integer.parseInt(string);
      }
    } catch (NumberFormatException e) {
    }
    return num;
  }

  public static double getDouble(String string) {
    double num = 0;
    try {
      if (com.droid.wiz.toolkits.utils.Tools.notEmpty(string)) {
        string = string.replaceAll(",", "");
        num = Double.parseDouble(string);
      }
    } catch (NumberFormatException e) {
    }
    return num;
  }

  public static float getFloat(String string) {
    float num = 0;
    try {
      if (com.droid.wiz.toolkits.utils.Tools.notEmpty(string)) {
        string = string.replaceAll(",", "");
        num = Float.parseFloat(string);
      }
    } catch (NumberFormatException e) {
    }
    return num;
  }


  /**
   * 保留两位小数
   *
   * @param d
   * @return
   */
  public static String getDecimalTwo(double d) {
    DecimalFormat df = new DecimalFormat("#.00");
    return df.format(d);
  }

  /**
   * 保留三位小数
   *
   * @param d
   * @return
   */
  public static String getDecimalThree(double d) {
    DecimalFormat df = new DecimalFormat("#.000");
    return df.format(d);
  }

  public static boolean isNumber(String string) {
    if (string == null) {
      return false;
    }
    Pattern pattern = Pattern.compile("^[+-]?[\\d]+([\\.][\\d]+)?([Ee][+-]?[\\d]+)?$");
    return pattern.matcher(string).matches();
  }

  public static String getPercent(double s) {
    NumberFormat numberFormat = NumberFormat.getPercentInstance();
    numberFormat.setMinimumFractionDigits(0);
    return numberFormat.format(s);
  }


  //如果有小数保留两位小数,如果没有小数则不带小数
  public static String getMoneyNoDecimals(String number) {
    if (TextUtils.isEmpty(number)) {
      return "";
    }

    if (number.contains(",")) {
      return number;
    }

    BigDecimal bigDecimal = BigDecimalWrapper.get(number);

    String pattern;
    if (number.endsWith(".00") || number.endsWith(".0") || !number.contains(".")) {
      pattern = "#,##0";
    } else {
      pattern = "#,##0.00";
    }

    DecimalFormat df = LanguageUtils.getDecimalFormat(pattern);
    return df.format(bigDecimal);
  }

  public static long getLong(String string) {
    long num = 0;
    try {
      if (Tools.notEmpty(string)) {
        string = string.replaceAll(",", "");
        num = Long.parseLong(string);
      }
    } catch (NumberFormatException e) {
    }
    return num;
  }
}
