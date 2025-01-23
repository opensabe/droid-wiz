package com.droid.wiz.toolkits.swipeback;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

public abstract class SwipeBackActivity extends AppCompatActivity implements SwipeBackActivityBase {

  private SwipeBackActivityHelper mHelper;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    try {
      mHelper = new SwipeBackActivityHelper(this);
      mHelper.onActivityCreate();
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    try {
      mHelper.onPostCreate();
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }

  @Override
  public <T extends View> T findViewById(@IdRes int id) {
    T v = super.findViewById(id);
    try {
      if (v == null && mHelper != null) {
        return mHelper.findViewById(id);
      }
    } catch (Throwable t) {
      t.printStackTrace();
    }
    return v;
  }

  @Override
  public com.droid.wiz.toolkits.swipeback.SwipeBackLayout getSwipeBackLayout() {
    try {
      return mHelper.getSwipeBackLayout();
    } catch (Throwable t) {
      t.printStackTrace();
    }
    return new SwipeBackLayout(this);
  }

  @Override
  public void setSwipeBackEnable(boolean enable) {
    try {
      getSwipeBackLayout().setEnableGesture(enable);
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }

  @Override
  public void scrollToFinishActivity() {
    try {
      SwipeBackUtils.convertActivityToTranslucent(this);
      getSwipeBackLayout().scrollToFinishActivity();
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
