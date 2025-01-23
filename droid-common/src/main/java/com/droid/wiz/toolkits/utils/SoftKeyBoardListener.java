package com.droid.wiz.toolkits.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * 软键盘监听
 */
public class SoftKeyBoardListener {

  private final View mRootView;
  private int mRootViewVisibleHeight;
  private OnSoftKeyBoardChangeListener mOnSoftKeyBoardChangeListener;
  private final ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener;

  public SoftKeyBoardListener(Activity activity) {
    mRootView = activity.getWindow().getDecorView();
    mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {
        Rect r = new Rect();
        mRootView.getWindowVisibleDisplayFrame(r);
        int visibleHeight = r.height();
        System.out.println("" + visibleHeight);
        if (mRootViewVisibleHeight == 0) {
          mRootViewVisibleHeight = visibleHeight;
          return;
        }
        if (mRootViewVisibleHeight == visibleHeight) {
          return;
        }
        if (mRootViewVisibleHeight - visibleHeight > 200) {
          if (mOnSoftKeyBoardChangeListener != null) {
            mOnSoftKeyBoardChangeListener.keyBoardShow(mRootViewVisibleHeight - visibleHeight);
          }
          mRootViewVisibleHeight = visibleHeight;
          return;
        }
        if (visibleHeight - mRootViewVisibleHeight > 200) {
          if (mOnSoftKeyBoardChangeListener != null) {
            mOnSoftKeyBoardChangeListener.keyBoardHide(visibleHeight - mRootViewVisibleHeight);
          }
          mRootViewVisibleHeight = visibleHeight;
        }
      }
    };
    mRootView.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
  }

  public void removeGlobalLayoutListener() {
    if (mRootView != null && mOnSoftKeyBoardChangeListener != null) {
      mRootView.getViewTreeObserver().removeOnGlobalLayoutListener(mOnGlobalLayoutListener);
    }
  }

  private void setOnSoftKeyBoardChangeListener(
      OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener) {
    this.mOnSoftKeyBoardChangeListener = onSoftKeyBoardChangeListener;
  }

  public interface OnSoftKeyBoardChangeListener {
    void keyBoardShow(int height);

    void keyBoardHide(int height);
  }

  public static SoftKeyBoardListener setListener(Activity activity,
      OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener) {
    SoftKeyBoardListener softKeyBoardListener = new SoftKeyBoardListener(activity);
    softKeyBoardListener.setOnSoftKeyBoardChangeListener(onSoftKeyBoardChangeListener);
    return softKeyBoardListener;
  }
}