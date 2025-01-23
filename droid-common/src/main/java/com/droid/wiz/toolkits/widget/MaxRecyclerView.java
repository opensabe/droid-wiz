package com.droid.wiz.toolkits.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class MaxRecyclerView extends RecyclerView {

  private int mMaxHeight;

  public MaxRecyclerView(@NonNull Context context) {
    this(context, null);
  }

  public MaxRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public void setMaxHeight(int maxHeight) {
    mMaxHeight = maxHeight;
    requestLayout();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    if (mMaxHeight > 0) {
      heightMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxHeight, MeasureSpec.AT_MOST);
    }
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }
}
