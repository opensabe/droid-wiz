package com.droid.wiz.toolkits.widget.selector;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Keep;
import androidx.annotation.Nullable;

/**
 * Created by liuXiao on 10/26/2021
 */
@Keep
public class MLinearLayout extends LinearLayout {
  private final SelectorHelper selectorHelper;

  public MLinearLayout(Context context) {
    this(context, null);
  }

  public MLinearLayout(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    selectorHelper = new SelectorHelper(this, attrs);
  }

  public void setNormalColor(int color) {
    selectorHelper.setNormalColor(color);
  }

  public void setNormalStyle(int color, float[] cornerRadii) {
    selectorHelper.setNormalStyle(color, cornerRadii);
  }

  public void setNormalStoke(int mOutlineWidth, int mOutlineColor) {
    selectorHelper.setNormalStoke(mOutlineWidth, mOutlineColor);
  }
}
