package com.droid.wiz.toolkits.swipeback;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import com.droid.wiz.toolkits.R;

/**
 * @author Yrom
 */
public class SwipeBackActivityHelper {

  private final Activity mActivity;

  private com.droid.wiz.toolkits.swipeback.SwipeBackLayout mSwipeBackLayout;

  public SwipeBackActivityHelper(Activity activity) {
    mActivity = activity;
  }

  @SuppressWarnings("deprecation")
  public void onActivityCreate() {
    mActivity.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    mActivity.getWindow().getDecorView().setBackgroundDrawable(null);
    mSwipeBackLayout =
        (com.droid.wiz.toolkits.swipeback.SwipeBackLayout) LayoutInflater.from(mActivity).inflate(R.layout.swipeback_layout, null);
  }

  public void onPostCreate() {
    mSwipeBackLayout.attachToActivity(mActivity);
  }

  public <T extends View> T findViewById(int id) {
    if (mSwipeBackLayout != null) {
      return mSwipeBackLayout.findViewById(id);
    }
    return null;
  }

  public SwipeBackLayout getSwipeBackLayout() {
    return mSwipeBackLayout;
  }
}
