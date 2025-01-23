package com.droid.wiz.toolkits.widget;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by fangzheng on 07/07/2020
 */
public class MPopupWindow extends PopupWindow {

  public MPopupWindow(Context context) {
    super(context);
  }

  public MPopupWindow(View contentView, int width, int height) {
    super(contentView, width, height);
  }

  @Override
  public void showAtLocation(View parent, int gravity, int x, int y) {
    try {
      super.showAtLocation(parent, gravity, x, y);
    } catch (Throwable ignored) {
    }
  }

  @Override
  public void showAsDropDown(View anchor, int xOff, int yOff) {
    try {
      super.showAsDropDown(anchor, xOff, yOff);
    } catch (Throwable ignored) {
    }
  }

  @Override
  public void dismiss() {
    try {
      super.dismiss();
    } catch (Throwable ignored) {
    }
  }
}
