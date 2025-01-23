package com.droid.wiz.toolkits.utils;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class ViewUtils {

  public static boolean isTouchView(View v, MotionEvent event) {
    if (v != null && event != null) {
      int[] leftTop = { 0, 0 };
      v.getLocationInWindow(leftTop);
      int left = leftTop[0];
      int top = leftTop[1];
      int bottom = top + v.getHeight();
      int right = left + v.getWidth();
      return !(event.getX() > left) || !(event.getX() < right) || !(event.getY() > top) ||
          !(event.getY() < bottom);
    }
    return false;
  }

  public static void removeParent(View view) {
    if (view == null || view.getParent() == null) {
      return;
    }
    ViewParent parent = view.getParent();
    if (parent instanceof ViewGroup) {
      ((ViewGroup) parent).removeView(view);
    }
  }
}
