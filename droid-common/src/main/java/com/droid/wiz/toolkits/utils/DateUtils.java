package com.droid.wiz.toolkits.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import com.droid.wiz.toolkits.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by liuXiao on 10/14/2021
 */
public class DateUtils {

  private static final long ONE_MINUTE = 60000;
  private static final long ONE_HOUR = ONE_MINUTE * 60;
  private static final long ONE_DAY = ONE_HOUR * 24;
  public static final long MILLISECONDS_PER_DAY = 86400000;
  public static final String HM_FORMAT = "HH:mm";
  public static final String DM_FORMAT = "dd/MM";
  public static final String DMY_FORMAT = "dd/MM/yyyy";
  public static final String DMY_FORMAT2 = "dd/MM/yy";
  public static final String EMD_FORMAT = "EEE d MMM";
  public static final String DMY_HM_FORMAT = "dd/MM/yyyy HH:mm";
  public static final String DMY_HM_FORMAT2 = "dd/MM/yyyy\nHH:mm";
  public static final String DD_MMM = "d MMM";
  private static final ThreadLocal<Map<String, SimpleDateFormat>> SDF_THREAD_LOCAL =
      new ThreadLocal<Map<String, SimpleDateFormat>>() {
        @Override
        protected Map<String, SimpleDateFormat> initialValue() {
          return new HashMap<>();
        }
      };

  @Nullable
  private static SimpleDateFormat getDateFormat(@NonNull final String pattern) {
    Map<String, SimpleDateFormat> sdfMap = SDF_THREAD_LOCAL.get();
    if (sdfMap == null) {
      return null;
    }
    SimpleDateFormat simpleDateFormat = sdfMap.get(pattern);
    if (simpleDateFormat == null) {
      simpleDateFormat = new SimpleDateFormat(pattern, Locale.US);
      sdfMap.put(pattern, simpleDateFormat);
    }
    return simpleDateFormat;
  }

  public static SimpleDateFormat getSafeDateFormat(@NonNull final String pattern) {
    SimpleDateFormat sdf = getDateFormat(pattern);
    if (sdf != null) {
      return sdf;
    }
    return new SimpleDateFormat(pattern, Locale.US);
  }

  public static String format(long timeMillis, String format) {
    return getSafeDateFormat(format).format(timeMillis);
  }

  public static String formatApiParam(long timeMillis) {
    final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";//传参格式,勿改动!!
    String format = format(timeMillis, DATETIME_FORMAT);
    return format + " " + getTimeZone();
  }

  public static String getHmFormat(long timeMillis) {
    return format(timeMillis, HM_FORMAT);
  }

  public static String getTimeDM(long timeMillis) {
    return format(timeMillis, DM_FORMAT);
  }

  public static String getTimeDMY(long timeMillis) {
    return format(timeMillis, DMY_FORMAT);
  }

  public static String getTime3MD(long timeMillis) {
    return format(timeMillis, DD_MMM);
  }

  public static String getTimeEMD(long timeMillis) {
    return format(timeMillis, EMD_FORMAT);
  }

  public static String getTimeDMYHM(long timeMillis) {
    return format(timeMillis, DMY_HM_FORMAT);
  }

  /**
   * 比较两个时间戳是不是同一天
   */
  public static boolean isSameDay(long timeOne, long timeTwo) {
    return getTimeDMY(timeOne).equalsIgnoreCase(getTimeDMY(timeTwo));
  }

  public static boolean isSameYear(long timeOne, long timeTwo) {
    return format(timeOne, "yyyy").equalsIgnoreCase(format(timeTwo, "yyyy"));
  }

  public static String getDateEngToday(long time) {
    if (isSameDay(time, System.currentTimeMillis())) {
      return "Today, " + getHmFormat(time);
    } else {
      return format(time, "d MMM HH:mm");
    }
  }

  /**
   * 获取时区
   */
  public static String getTimeZone() {
    TimeZone tz = TimeZone.getDefault();
    return tz.getDisplayName(false, TimeZone.SHORT).replace(":", "");
  }

  /**
   * 时间戳转换为0点0分0秒的时间戳
   *
   * @param millis 时间戳
   * @return 0点0分0秒的时间戳
   */
  public static long getZeroMillis(long millis) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(millis);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getTimeInMillis();
  }

  /**
   * 获取几天后0点0分0秒的时间戳
   */
  public static long getDiffZeroMillis(int diff) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_MONTH, diff);
    return getZeroMillis(calendar.getTimeInMillis());
  }

  /**
   * 获取某年份1月1日的时间戳
   */
  public static long getYearStartTimeMillis(int year) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(year, Calendar.JANUARY, 1, 0, 0, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getTimeInMillis();
  }

  /**
   * 时间戳转换为0点0分0秒的时间戳
   *
   * @return 今天0点0分0秒的时间戳
   */
  public static long getZeroMillis() {
    return getZeroMillis(System.currentTimeMillis());
  }

  /**
   * 返回前dif天或者后dif天
   */
  public static Long getDate(int dif) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(Calendar.DAY_OF_MONTH, dif);
    return calendar.getTimeInMillis();
  }

  /**
   * 获取两个时间戳相差几天
   */
  public static int differentDays(long timeMillis1, long timeMillis2) {
    return (int) (timeMillis1 - timeMillis2) / (1000 * 3600 * 24);
  }

  /**
   * 获取年月日
   *
   * @param millis 时间戳毫秒
   * @return 数组 [1] = year, [2] = month, [3] = day
   */
  public static int[] getYearMonthDay(long millis) {
    int[] array = new int[3];
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date(millis));
    array[0] = calendar.get(Calendar.YEAR);
    array[1] = calendar.get(Calendar.MONTH) + 1;
    array[2] = calendar.get(Calendar.DAY_OF_MONTH);
    return array;
  }

  public static int getDayDif(Long start, Long end) {
    if (start == null || end == null) {
      return 0;
    }
    long betweenDays = (end - start) / (1000 * 3600 * 24);
    return Integer.parseInt(String.valueOf(betweenDays));
  }

  public static String formatAgo(long time) {
    long ago = System.currentTimeMillis() - time;
    if (ago <= ONE_MINUTE) {
      return com.droid.wiz.toolkits.utils.Tools.getString(R.string.just_now);
    }
    if (ago < ONE_HOUR) {
      long minutes = ago / ONE_MINUTE;
      return com.droid.wiz.toolkits.utils.Tools.formatString(minutes > 1 ? R.string.minutes_ago : R.string.minute_ago, minutes);
    }
    if (ago < ONE_DAY) {
      long hour = ago / ONE_HOUR;
      return com.droid.wiz.toolkits.utils.Tools.formatString(hour > 1 ? R.string.hours_ago : R.string.hour_ago, hour);
    }
    if (ago < ONE_DAY * 7) {
      long day = ago / ONE_DAY;
      return com.droid.wiz.toolkits.utils.Tools.formatString(day > 1 ? R.string.days_ago : R.string.day_ago, day);
    }
    if (ago <= ONE_DAY * 29) {
      long week = ago / ONE_DAY / 7;
      return com.droid.wiz.toolkits.utils.Tools.formatString(week > 1 ? R.string.weeks_ago : R.string.week_ago, week);
    }
    if (ago <= ONE_DAY * 365) {
      long month = Math.max(1, ago / ONE_DAY / 30);
      return Tools.formatString(month > 1 ? R.string.months_ago : R.string.month_ago, month);
    }
    return format(time, DMY_FORMAT);
  }
}
