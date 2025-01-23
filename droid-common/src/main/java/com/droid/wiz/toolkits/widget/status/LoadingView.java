package com.droid.wiz.toolkits.widget.status;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import com.droid.wiz.toolkits.R;
import com.droid.wiz.toolkits.utils.Tools;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@Keep
public class LoadingView extends LinearLayout {

  public static final int COMMON = 0;
  public static final int SUBMIT = 1;
  public static final int LINE_WIDTH = Tools.dp2px(6);
  private View mLineView1, mLineView2, mLineView3;
  private ObjectAnimator mObjectAnimator1, mObjectAnimator2, mObjectAnimator3;
  private int mHeight;

  public LoadingView(Context context) {
    this(context, null);
  }

  public LoadingView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    setGravity(Gravity.CENTER);
    setType(COMMON);
  }

  public void setType(int type) {
    removeAllViews();
    int height1, height2, height3;
    int color1, color2, color3;
    if (type == SUBMIT) {
      height1 = Tools.dp2px(14);
      height2 = Tools.dp2px(8);
      height3 = Tools.dp2px(10);
      color1 = Tools.getColor(R.color.white);
      color2 = Tools.getColor(R.color.white);
      color3 = Tools.getColor(R.color.white);
    } else {
      height1 = Tools.dp2px(19);
      height2 = Tools.dp2px(14);
      height3 = Tools.dp2px(15);
      color1 = Tools.getColor(R.color.color2851C5);
      color2 = Tools.getColor(R.color.color5938AC);
      color3 = Tools.getColor(R.color.colorCE3D28);
    }
    int leftMargin = Tools.dp2px(1);
    mLineView1 = createLineView(height1, 0, color1);
    mLineView2 = createLineView(height2, leftMargin, color2);
    mLineView3 = createLineView(height3, leftMargin, color3);
    mHeight = (int) (height1 * 1.3f);
    addView(mLineView1);
    addView(mLineView2);
    addView(mLineView3);
  }

  private View createLineView(int height, int leftMargin, int color) {
    View view = new View(getContext());
    view.setBackgroundColor(color);
    Tools.setOutlineProvider(view, LINE_WIDTH / 2f);
    LayoutParams params = new LayoutParams(LINE_WIDTH, height);
    params.leftMargin = leftMargin;
    view.setLayoutParams(params);
    return view;
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    if (mHeight > 0) {
      heightMeasureSpec = MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY);
    }
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }

  private void start() {
    stop();
    mObjectAnimator1 =
        ObjectAnimator.ofFloat(mLineView1, "scaleY", 1f, 1.3f, 1f, 0.8f, 1f, 1.3f, 1f, 0.8f, 1f,
            1.3f, 1f, 0.8f);
    mObjectAnimator1.setInterpolator(new LinearInterpolator());
    mObjectAnimator1.setDuration(4000);
    mObjectAnimator1.setRepeatCount(ValueAnimator.INFINITE);
    mObjectAnimator1.start();

    mObjectAnimator2 =
        ObjectAnimator.ofFloat(mLineView2, "scaleY", 1f, 0.8f, 1f, 1.2f, 1f, 0.8f, 1f, 1.2f, 1f,
            0.8f, 1f, 1.2f);
    mObjectAnimator2.setInterpolator(new LinearInterpolator());
    mObjectAnimator2.setDuration(4000);
    mObjectAnimator2.setRepeatCount(ValueAnimator.INFINITE);
    mObjectAnimator2.start();

    mObjectAnimator3 =
        ObjectAnimator.ofFloat(mLineView3, "scaleY", 1.2f, 1f, 1.5f, 1f, 0.7f, 1f, 1.2f, 1.5f, 1f,
            0.7f, 1f, 1.2f);
    mObjectAnimator3.setInterpolator(new LinearInterpolator());
    mObjectAnimator3.setDuration(4000);
    mObjectAnimator3.setRepeatCount(ValueAnimator.INFINITE);
    mObjectAnimator3.start();
  }

  public void stop() {
    if (mObjectAnimator1 != null) {
      mObjectAnimator1.removeAllListeners();
      mObjectAnimator1.cancel();
      mObjectAnimator1 = null;
    }
    if (mObjectAnimator2 != null) {
      mObjectAnimator2.removeAllListeners();
      mObjectAnimator2.cancel();
      mObjectAnimator2 = null;
    }
    if (mObjectAnimator3 != null) {
      mObjectAnimator3.removeAllListeners();
      mObjectAnimator3.cancel();
      mObjectAnimator3 = null;
    }
  }

  @Override
  protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
    super.onVisibilityChanged(changedView, visibility);
    if (visibility == VISIBLE) {
      start();
    } else {
      stop();
    }
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    start();
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    stop();
  }
}
