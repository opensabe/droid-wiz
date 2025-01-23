package com.droid.wiz.toolkits.base;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.droid.wiz.toolkits.R;
import com.droid.wiz.toolkits.bus.MBus;
import com.droid.wiz.toolkits.bus.MReceiver;
import com.droid.wiz.toolkits.bus.OnReceiverListener;
import com.droid.wiz.toolkits.dialog.LoadingDialog;
import com.droid.wiz.toolkits.net.HttpUtils;
import com.droid.wiz.toolkits.net.request.GetRequest;
import com.droid.wiz.toolkits.net.request.PostRequest;
import com.droid.wiz.toolkits.swipeback.SwipeBackActivityBase;
import com.droid.wiz.toolkits.swipeback.SwipeBackActivityHelper;
import com.droid.wiz.toolkits.swipeback.SwipeBackLayout;
import com.droid.wiz.toolkits.swipeback.SwipeBackUtils;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by liuXiao on 07/15/2021
 */
public abstract class BaseActivity extends AppCompatActivity implements SwipeBackActivityBase {

  public LoadingDialog mLoadingDialog;
  protected BaseActivity mActivity;
  private SwipeBackActivityHelper mHelper;
  private MReceiver mReceiver;
  private boolean hasExecuteOnceOnResume;

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    try {
      mHelper.onPostCreate();
    } catch (Throwable t) {
      Log.e("mobile", "onCreate: " + t.getMessage());
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
      Log.e("mobile", "onCreate: " + t.getMessage());
    }
    return v;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mActivity = this;
    try {
      if (openSlideAnim() && lessOrEqualTiramisu()) {
        overridePendingTransition(R.anim.slide_right_in, R.anim.in_from_right_abit);
      }
      mHelper = new SwipeBackActivityHelper(this);
      mHelper.onActivityCreate();
    } catch (Throwable t) {
      Log.e("mobile", "onCreate: " + t.getMessage());
    }
  }

  @Override
  protected void onPause() {
    try {
      if (openSlideAnim() && lessOrEqualTiramisu()) {
        overridePendingTransition(R.anim.out_to_right_abit, R.anim.slide_right_out);
      }
    } catch (Throwable t) {
      Log.e("mobile", "onCreate: " + t.getMessage());
    }
    super.onPause();
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (!hasExecuteOnceOnResume) {
      executeOnceOnResume();
      hasExecuteOnceOnResume = true;
    }
  }

  protected void registerBus(@NonNull OnReceiverListener onReceiverListener, String... code) {
    if (mReceiver != null) {
      mReceiver.addCode(code);
      return;
    }
    MBus.register(mReceiver = new MReceiver(code) {
      @Override
      protected void onReceiver(@NonNull String code, @Nullable Object data) {
        onReceiverListener.onReceiver(code, data);
      }
    }.bindLifecycle(this));
  }

  public GetRequest get(@NonNull String url) {
    return HttpUtils.get(url).bindLifecycle(this);
  }

  public PostRequest post(@NonNull String url) {
    return HttpUtils.post(url).bindLifecycle(this);
  }

  @Override
  public SwipeBackLayout getSwipeBackLayout() {
    try {
      return mHelper.getSwipeBackLayout();
    } catch (Throwable t) {
      Log.e("mobile", "onCreate: " + t.getMessage());
    }
    return new SwipeBackLayout(this);
  }

  @Override
  public void setSwipeBackEnable(boolean enable) {
    try {
      getSwipeBackLayout().setEnableGesture(enable);
    } catch (Throwable t) {
      Log.e("mobile", "onCreate: " + t.getMessage());
    }
  }

  @Override
  public void scrollToFinishActivity() {
    try {
      SwipeBackUtils.convertActivityToTranslucent(this);
      getSwipeBackLayout().scrollToFinishActivity();
    } catch (Throwable t) {
      Log.e("mobile", "onCreate: " + t.getMessage());
    }
  }

  public void showLoadingDialog() {
    if (mLoadingDialog == null) {
      mLoadingDialog = new LoadingDialog(mActivity);
    }
    mLoadingDialog.show();
  }

  public void dismissLoadingDialog() {
    if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
      mLoadingDialog.dismiss();
    }
  }

  @SuppressLint("MissingSuperCall")
  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
  }

  private boolean lessOrEqualTiramisu() {
    return Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU;
  }

  public boolean openSlideAnim() {
    return true;
  }

  public void executeOnceOnResume() {

  }
}
