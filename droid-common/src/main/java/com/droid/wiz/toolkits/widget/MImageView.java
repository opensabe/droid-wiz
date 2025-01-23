package com.droid.wiz.toolkits.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.droid.wiz.toolkits.R;
import com.google.android.material.imageview.ShapeableImageView;

import androidx.annotation.Keep;
import androidx.annotation.Nullable;

/**
 * Created by liuXiao on 07/12/2021
 * 继承 ShapeableImageView 支持设置边框/颜色/圆角/宽高比
 */
@Keep
public class MImageView extends ShapeableImageView {
  public float mRatio;

  public MImageView(Context context) {
    this(context, null);
  }

  public MImageView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MImageView);
    mRatio = typedArray.getFloat(R.styleable.RatioImageView_m_ratio, 0f);
    typedArray.recycle();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    if (mRatio != 0) {
      int width = MeasureSpec.getSize(widthMeasureSpec);
      float height = width * mRatio;
      setMeasuredDimension(width, (int) height);
    } else {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
  }

}
