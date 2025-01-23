package com.droid.wiz.toolkits.widget.selector;

import java.util.ArrayList;
import java.util.List;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.droid.wiz.toolkits.R;

/**
 * Created by liuXiao on 10/26/2021
 * 支持设置圆角/边框/背景颜色/水波纹颜色/渐变
 */
public class SelectorHelper {

  private static final GradientDrawable.Orientation[] GRADIENT_ORIENTATION =
      { GradientDrawable.Orientation.TOP_BOTTOM, GradientDrawable.Orientation.TR_BL,
          GradientDrawable.Orientation.RIGHT_LEFT, GradientDrawable.Orientation.BR_TL,
          GradientDrawable.Orientation.BOTTOM_TOP, GradientDrawable.Orientation.BL_TR,
          GradientDrawable.Orientation.LEFT_RIGHT, GradientDrawable.Orientation.TL_BR };

  private final View mView;
  private float[] mCornerRadii; //圆角
  private int mNormalColor; //默认背景色
  private int mSelectColor; //代码设置setSelect为true时颜色
  private int mUnableColor; //代码设置enabled为false时颜色
  private int mRippleColor; //水波纹颜色
  private int mOutlineWidth; //边框线宽度
  private int mOutlineColor; //边框线颜色
  private int mGradientType; //渐变类型
  private int mGradientOrientation; //渐变方向
  private float mGradientRadius; //圆角
  private float mGradientCenterX; //渐变x
  private float mGradientCenterY; //渐变y
  private int mGradientStartColor; //渐变开始颜色
  private int mGradientCenterColor;//渐变中间颜色
  private int mGradientEndColor; //渐变结束颜色
  private GradientDrawable normalDrawable;

  public SelectorHelper(View view, AttributeSet attributeSet) {
    mView = view;
    initAttributeSet(attributeSet);
    setup();
  }

  private void initAttributeSet(AttributeSet attributeSet) {
    TypedArray a =
        mView.getContext().obtainStyledAttributes(attributeSet, R.styleable.MLinearLayout);
    float cornerRadius = a.getDimension(R.styleable.MLinearLayout_sCornerRadius, 0);
    float tl = a.getDimension(R.styleable.MLinearLayout_sCornerRadiusTL, cornerRadius);
    float tr = a.getDimension(R.styleable.MLinearLayout_sCornerRadiusTR, cornerRadius);
    float bl = a.getDimension(R.styleable.MLinearLayout_sCornerRadiusBL, cornerRadius);
    float br = a.getDimension(R.styleable.MLinearLayout_sCornerRadiusBR, cornerRadius);
    mNormalColor = a.getColor(R.styleable.MLinearLayout_sNormalColor, 0);
    mSelectColor = a.getColor(R.styleable.MLinearLayout_sSelectColor, 0);
    mUnableColor = a.getColor(R.styleable.MLinearLayout_sUnableColor, 0);
    mRippleColor = a.getColor(R.styleable.MLinearLayout_sRippleColor, 0);
    mOutlineWidth = a.getDimensionPixelOffset(R.styleable.MLinearLayout_sOutlineWidth, 0);
    mOutlineColor = a.getColor(R.styleable.MLinearLayout_sOutlineColor, 0);
    mGradientType = a.getInt(R.styleable.MLinearLayout_sGradientType, -1);
    mGradientOrientation = a.getInt(R.styleable.MLinearLayout_sGradientOrientation, 0);
    mGradientRadius = a.getFloat(R.styleable.MLinearLayout_sGradientRadius, 0);
    mGradientCenterX = a.getFloat(R.styleable.MLinearLayout_sGradientCenterX, 0);
    mGradientCenterY = a.getFloat(R.styleable.MLinearLayout_sGradientCenterY, 0);
    mGradientStartColor = a.getColor(R.styleable.MLinearLayout_sGradientStartColor, -1);
    mGradientCenterColor = a.getColor(R.styleable.MLinearLayout_sGradientCenterColor, -1);
    mGradientEndColor = a.getColor(R.styleable.MLinearLayout_sGradientEndColor, -1);
    a.recycle();
    mCornerRadii = new float[] { tl, tl, tr, tr, br, br, bl, bl };
  }

  private void setup() {
    StateListDrawable stateListDrawable = new StateListDrawable();
    if (mUnableColor != 0) {
      GradientDrawable unableDrawable = new GradientDrawable();
      setDrawable(unableDrawable, mUnableColor, false);
      stateListDrawable.addState(new int[] { -android.R.attr.state_enabled }, unableDrawable);
    }
    if (mSelectColor != 0) {
      GradientDrawable selectDrawable = new GradientDrawable();
      setDrawable(selectDrawable, mSelectColor, false);
      stateListDrawable.addState(new int[] { android.R.attr.state_selected }, selectDrawable);
    }
    normalDrawable = new GradientDrawable();
    setDrawable(normalDrawable, mNormalColor, true);
    stateListDrawable.addState(new int[] {}, normalDrawable);
    if (mRippleColor != 0) {
      RippleDrawable rippleDrawable =
          new RippleDrawable(ColorStateList.valueOf(mRippleColor), stateListDrawable, null);
      mView.setBackground(rippleDrawable);
    } else {
      mView.setBackground(stateListDrawable);
    }
  }

  public void setDrawable(GradientDrawable drawable, int color, boolean useGradient) {
    drawable.setColor(color);
    drawable.setCornerRadii(mCornerRadii);
    drawable.setStroke(mOutlineWidth, mOutlineColor);
    if (useGradient && mGradientType != -1) {
      drawable.setGradientType(mGradientType);
      drawable.setGradientRadius(mGradientRadius);
      drawable.setGradientCenter(mGradientCenterX, mGradientCenterY);
      drawable.setOrientation(GRADIENT_ORIENTATION[mGradientOrientation]);
      if (generateColors() != null) {
        drawable.setColors(generateColors());
      }
    }
  }

  private int[] generateColors() {
    List<Integer> tempList = new ArrayList<>(3);
    if (mGradientStartColor != -1) {
      tempList.add(mGradientStartColor);
    }
    if (mGradientCenterColor != -1) {
      tempList.add(mGradientCenterColor);
    }
    if (mGradientEndColor != -1) {
      tempList.add(mGradientEndColor);
    }
    if (tempList.size() < 2) {
      return null;
    }
    int[] colors = new int[tempList.size()];
    for (int i = 0; i < tempList.size(); i++) {
      colors[i] = tempList.get(i);
    }
    return colors;
  }

  public void setNormalColor(int color) {
    normalDrawable.setColor(color);
  }

  public void setNormalStyle(int color, float[] cornerRadii) {
    normalDrawable.setColor(color);
    normalDrawable.setCornerRadii(cornerRadii);
  }

  public void setNormalStoke(int mOutlineWidth, int mOutlineColor) {
    normalDrawable.setStroke(mOutlineWidth, mOutlineColor);
  }
}
