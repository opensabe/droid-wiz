package com.droid.wiz.toolkits.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by fangzheng on 07/12/2021
 */
public class LanguageUtils {

  //DecimalFormat与语言环境有关
  public static DecimalFormat getDecimalFormat(String pattern) {
    DecimalFormat df = new DecimalFormat(pattern);
    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    symbols.setDecimalSeparator('.');
    df.setDecimalFormatSymbols(symbols);
    return df;
  }
}
