package com.droid.wiz.toolkits.widget.selector;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by liuXiao on 10/26/2021
 */
@Keep
public class MTextView extends androidx.appcompat.widget.AppCompatTextView {

  private final SelectorHelper selectorHelper;

  public MTextView(@NonNull Context context) {
    this(context, null);
  }

  public MTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    selectorHelper = new SelectorHelper(this, attrs);
  }

  public void setNormalColor(int color){
    selectorHelper.setNormalColor(color);
  }

  public void setNormalStyle(int color,float[] cornerRadii){
    selectorHelper.setNormalStyle(color,cornerRadii);
  }

  public void setNormalStoke(int mOutlineWidth, int mOutlineColor) {
    selectorHelper.setNormalStoke(mOutlineWidth, mOutlineColor);
  }
}
