package com.droid.wiz.toolkits.base;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by liuXiao on 10/13/2021
 * 封装BaseDialog
 */
public class BaseDialog extends Dialog {

  public static int WRAP_CONTENT = WindowManager.LayoutParams.WRAP_CONTENT;
  public static int MATCH_PARENT = WindowManager.LayoutParams.MATCH_PARENT;

  private int mWidth = WRAP_CONTENT; //宽
  private int mHeight = WRAP_CONTENT; //高
  private float mDimAmount = 0.5f; //蒙层透明度
  private int mGravity = Gravity.CENTER; //弹框位置
  private int mX, mY; //x,y
  private int mWindowAnimations; //动画

  public BaseDialog(Context context) {
    super(context);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    initWindow();
    setUp();
  }

  /**
   * 去除系统自带的间距
   */
  private void initWindow() {
    Window window = getWindow();
    if (window != null) {
      window.setBackgroundDrawableResource(android.R.color.transparent);
      window.getDecorView().setPadding(0, 0, 0, 0);
    }
  }

  public BaseDialog setWidth(int width) {
    mWidth = width;
    return this;
  }

  public BaseDialog setHeight(int height) {
    mHeight = height;
    return this;
  }

  public BaseDialog setDimAmount(float dimAmount) {
    mDimAmount = dimAmount;
    return this;
  }

  public BaseDialog setGravity(int gravity) {
    mGravity = gravity;
    return this;
  }

  public BaseDialog setXY(int x, int y) {
    mX = x;
    mY = y;
    return this;
  }

  public BaseDialog setWindowAnimations(int windowAnimations) {
    mWindowAnimations = windowAnimations;
    return this;
  }

  /**
   * 设置完属性后,必须调用本方法属性才会生效
   */
  public void setUp() {
    Window window = getWindow();
    if (window != null) {
      WindowManager.LayoutParams attributes = window.getAttributes();
      attributes.width = mWidth;
      attributes.height = mHeight;
      attributes.dimAmount = mDimAmount;
      attributes.gravity = mGravity;
      attributes.x = mX;
      attributes.y = mY;
      window.setAttributes(attributes);
      if (mWindowAnimations != 0) {
        window.setWindowAnimations(mWindowAnimations);
      }
    }
  }

  public void show() {
    try {
      super.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void dismiss() {
    try {
      super.dismiss();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
