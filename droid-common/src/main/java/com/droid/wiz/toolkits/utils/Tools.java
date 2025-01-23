package com.droid.wiz.toolkits.utils;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.droid.wiz.toolkits.R;

import androidx.annotation.ArrayRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

/**
 * Created by liuXiao on 07/08/2021
 */
public class Tools {

  public static Application sApplication;

  public static Resources sResources;

  public static void init(Application application) {
    sApplication = application;
  }

  public static void updateResource(Resources resources) {
    sResources = resources;
  }

  public static Context getContext() {
    return sApplication;
  }

  public static Application getApplication() {
    return sApplication;
  }

  public static Resources getResources() {
    if (sResources == null) {
      sResources = getContext().getResources();
    }
    return sResources;
  }

  public static String getString(@StringRes int id) {
    return getResources().getString(id);
  }

  public static int getColor(@ColorRes int id) {
    return getResources().getColor(id);
  }

  public static float getDimension(@DimenRes int id) {
    return getResources().getDimension(id);
  }

  public static int getDimensionPixelOffset(@DimenRes int id) {
    return getResources().getDimensionPixelOffset(id);
  }

  public static Drawable getDrawable(@DrawableRes int id) {
    return ContextCompat.getDrawable(getContext(), id);
  }

  public static void setDrawable(@NonNull ImageView iv, @Nullable @DrawableRes Integer id) {
    if (id == null) {
      return;
    }
    iv.setImageDrawable(ContextCompat.getDrawable(getContext(), id));
  }

  public static String[] getStringArray(@ArrayRes int id) {
    return getResources().getStringArray(id);
  }

  public static int dp2px(float dpValue) {
    return (int) (dpValue * (getResources().getDisplayMetrics().density) + 0.5f);
  }

  public static int sp2Px(float spVal) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal,
        getResources().getDisplayMetrics());
  }

  public static int getScreenWidth() {
    return getResources().getDisplayMetrics().widthPixels;
  }

  public static int getScreenHeight() {
    return getResources().getDisplayMetrics().heightPixels;
  }

  public static int getScreenRealHeight() {
    WindowManager windowManager =
        (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
    Display defaultDisplay = windowManager.getDefaultDisplay();
    Point point = new Point();
    defaultDisplay.getRealSize(point);
    return point.y;
  }

  public static int getStatusBarHeight() {
    int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
      return getDimensionPixelOffset(resourceId);
    }
    return 0;
  }

  public static int getNavigationBarHeight() {
    int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
    if (resourceId > 0) {
      return getDimensionPixelOffset(resourceId);
    }
    return 0;
  }

  public static boolean isSmallScreen() {
    return getScreenHeight() <= 1350;
  }

  public static void transparentStatusBar(Window window) {
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    window.getDecorView().setSystemUiVisibility(
        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    window.setStatusBarColor(Color.TRANSPARENT);
  }

  /**
   * eg:view.getContext/dialog.getContext
   *
   * @param context Context
   * @return Activity
   */
  @Nullable
  public static Activity getActivityByContext(@Nullable Context context) {
    if (context == null) {
      return null;
    }

    if (context instanceof Activity) {
      return (Activity) context;
    }

    if (context instanceof Application || context instanceof Service) {
      return null;
    }

    while (context instanceof ContextWrapper) {
      if (context instanceof Activity) {
        return (Activity) context;
      }
      context = ((ContextWrapper) context).getBaseContext();
    }
    return null;
  }

  public static void showKeyboard(@NonNull View view) {
    InputMethodManager imm =
        (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    if (imm != null) {
      view.requestFocus();
      imm.showSoftInput(view, 0);
    }
  }

  public static void hideKeyboard(@NonNull View view) {
    InputMethodManager imm =
        (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    if (imm != null) {
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
  }

  public static boolean isEmpty(@Nullable CharSequence charSequence) {
    return charSequence == null || charSequence.length() == 0 ||
        "null".equalsIgnoreCase(charSequence + "");
  }

  public static boolean notEmpty(@Nullable CharSequence charSequence) {
    return !isEmpty(charSequence);
  }

  public static String formatString(String format, Object... args) {
    try {
      return String.format(Locale.US, format, args);
    } catch (Exception e) {
      e.printStackTrace();
      return format;
    }
  }

  public static String formatString(int resId, Object... args) {
    String string = getString(resId);
    return formatString(string, args);
  }

  public static boolean equals(@Nullable String paramFirst, @Nullable String paramSecond) {
    return equals(paramFirst, paramSecond, false);
  }

  public static boolean equalsIgnoreCase(@Nullable String paramFirst,
      @Nullable String paramSecond) {
    return equals(paramFirst, paramSecond, true);
  }

  private static boolean equals(@Nullable String paramFirst, @Nullable String paramSecond,
      boolean ignoreCase) {
    if (paramFirst == null && paramSecond == null) {
      return true;
    }
    if (paramFirst == null || paramSecond == null) {
      return false;
    }
    return ignoreCase ? paramFirst.equalsIgnoreCase(paramSecond) : paramFirst.equals(paramSecond);
  }

  private static String subString(String txt, int start, int end) {
    if (Tools.isEmpty(txt)) {
      return "";
    }
    if (start >= 0 && end <= txt.length()) {
      return txt.substring(start, end);
    }
    return "";
  }

  public static boolean notEmpty(List<?> list) {
    return list != null && list.size() > 0;
  }

  public static boolean isEmpty(List<?> list) {
    return list == null || list.isEmpty();
  }

  public static boolean notEmpty(Set<?> set) {
    return set != null && set.size() > 0;
  }

  public static boolean isEmpty(Set<?> set) {
    return set == null || set.isEmpty();
  }

  public static boolean isEmpty(Map<?, ?> map) {
    return map == null || map.isEmpty();
  }

  public static boolean notEmpty(Map<?, ?> map) {
    return !isEmpty(map);
  }

  public static boolean remove(List<?> list, int position) {
    if (notEmpty(list) && position >= 0 && position < list.size()) {
      list.remove(position);
      return true;
    }
    return false;
  }

  public static boolean checkEmail(String email) {
    return email.matches(
        "^[a-z0-9A-Z]+([_\\-\\.][A-Za-z0-9]+)*@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
  }

  public static int formatColor(String color) {
    return formatColor(color, Tools.getColor(R.color.transparent));
  }

  public static int formatColor(String color, int defColor) {
    try {
      if (notEmpty(color)) {
        if (!color.startsWith("#")) {
          return Color.parseColor("#".concat(color));
        }
        return Color.parseColor(color);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return defColor;
  }

  public static boolean isZero(String value) {
    if (Tools.isEmpty(value)) {
      return false;
    }
    return value.equals("0.00") || value.equals("0") || value.equals("0.0");
  }

  public static boolean isEmptyOrZero(@Nullable String value) {
    if (Tools.isEmpty(value)) {
      return true;
    }
    return isZero(value);
  }

  public static void setLightStatusBar(Activity activity, boolean dark) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      View decor = activity.getWindow().getDecorView();
      if (dark) {
        decor.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
      } else {
        decor.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
      }
    }
  }

  public static Object getMapValue(Map<String, Object> map, String key, Object defaultValue) {
    if (map == null) {
      return defaultValue;
    }
    Object value = map.get(key);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }

  public static String removeDecimals(String text) {
    if (text == null) {
      return "";
    }
    return text.replaceAll("[.](.*)", "");
  }

  public static void setViewHeight(View view, int height) {
    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
    layoutParams.height = height;
    view.setLayoutParams(layoutParams);
  }

  public static void setViewWidth(View view, int width) {
    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
    layoutParams.width = width;
    view.setLayoutParams(layoutParams);
  }

  public static void setOutlineProvider(View view, float radius) {
    if (view == null) {
      return;
    }
    view.setOutlineProvider(new ViewOutlineProvider() {
      @Override
      public void getOutline(View view, Outline outline) {
        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), radius);
      }
    });
    view.setClipToOutline(true);
  }

  public static void setCircleView(View view) {
    if (view == null) {
      return;
    }
    view.setOutlineProvider(new ViewOutlineProvider() {
      @Override
      public void getOutline(View view, Outline outline) {
        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), view.getHeight() / 2f);
      }
    });
    view.setClipToOutline(true);
  }

  public static void setTextBold(TextView textView, boolean bold) {
    TextPaint paint = textView.getPaint();
    paint.setStyle(Paint.Style.FILL_AND_STROKE);
    paint.setStrokeWidth(bold ? 0.4f * (getResources().getDisplayMetrics().density) : 0f);
  }

  public static void setTextBold(boolean bold, TextView... textViews) {
    for (TextView textView : textViews) {
      setTextBold(textView, bold);
    }
  }

  public static Bitmap loadBitmap(View v) {
    int measuredWidth =
        View.MeasureSpec.makeMeasureSpec(v.getLayoutParams().width, View.MeasureSpec.EXACTLY);
    int measuredHeight =
        View.MeasureSpec.makeMeasureSpec(v.getLayoutParams().height, View.MeasureSpec.EXACTLY);
    v.measure(measuredWidth, measuredHeight);
    v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
    v.buildDrawingCache();
    return v.getDrawingCache();
  }

  public static void setViewTopMargin(View view, int topMargin) {
    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
    params.topMargin = topMargin;
    view.setLayoutParams(params);
  }

  public static void setText(TextView tv, String text) {
    if (Tools.notEmpty(text)) {
      tv.setText(text);
    } else {
      tv.setText("-");
    }
  }

  @NonNull
  public static Uri parse(String s) {
    if (isEmpty(s)) {
      return Uri.parse("");
    }
    return Uri.parse(s);
  }

  /**
   * 先从打包参数获online取是否线上环境,
   * 如果为null再取配置参数manualOnline
   *
   * @return Boolean 是否为线上环境
   */
  @SuppressWarnings("all")
  public static boolean isOnline() {
    return com.droid.wiz.toolkits.BuildConfig.online;
  }

  /**
   * 拼接字符串
   */
  public static String concat(Object... args) {
    StringBuilder sb = new StringBuilder();
    for (Object arg : args) {
      sb.append(arg);
    }
    return sb.toString();
  }

  /**
   * 把字符串放到剪贴板中
   *
   * @param text 字符串文本
   */
  public static void setClipText(String text) {
    if (isEmpty(text)) {
      return;
    }
    try {
      ClipboardManager cm =
          (ClipboardManager) getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
      ClipData clip = ClipData.newPlainText("", text);
      cm.setPrimaryClip(clip);
    } catch (Exception e) {
    }
  }
}
