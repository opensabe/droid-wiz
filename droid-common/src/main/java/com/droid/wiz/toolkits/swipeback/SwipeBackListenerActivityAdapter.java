package com.droid.wiz.toolkits.swipeback;

import java.lang.ref.WeakReference;

import android.app.Activity;

import androidx.annotation.NonNull;

/**
 * Created by laysionqet on 2018/4/24.
 */
public class SwipeBackListenerActivityAdapter implements SwipeBackLayout.SwipeListenerEx {
  private final WeakReference<Activity> mActivity;

  public SwipeBackListenerActivityAdapter(@NonNull Activity activity) {
    mActivity = new WeakReference<>(activity);
  }

  @Override
  public void onScrollStateChange(int state, float scrollPercent) {

  }

  @Override
  public void onEdgeTouch(int edgeFlag) {
    try {
      Activity activity = mActivity.get();
      if (null != activity) {
        SwipeBackUtils.convertActivityToTranslucent(activity);
      }
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }

  @Override
  public void onScrollOverThreshold() {

  }

  @Override
  public void onContentViewSwipedBack() {
    try {
      Activity activity = mActivity.get();
      if (null != activity && !activity.isFinishing()) {
        activity.finish();
        activity.overridePendingTransition(0, 0);
      }
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
