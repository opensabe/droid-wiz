package com.droid.wiz.toolkits.widget.ratio;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.droid.wiz.toolkits.R;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * Created by liuXiao on 07/08/2021
 */
@Keep
public class RatioImageView extends AppCompatImageView {
  public float mRatio;

  public RatioImageView(@NonNull Context context) {
    this(context, null);
  }

  public RatioImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView);
    mRatio = typedArray.getFloat(R.styleable.RatioImageView_m_ratio, 0f);
    typedArray.recycle();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    if (mRatio != 0) {
      int width = MeasureSpec.getSize(widthMeasureSpec);
      super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
          MeasureSpec.makeMeasureSpec((int) (width * mRatio), MeasureSpec.EXACTLY));
    } else {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
  }
}
