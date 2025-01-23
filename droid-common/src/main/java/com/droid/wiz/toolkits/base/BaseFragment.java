package com.droid.wiz.toolkits.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.droid.wiz.toolkits.bus.MBus;
import com.droid.wiz.toolkits.bus.MReceiver;
import com.droid.wiz.toolkits.bus.OnReceiverListener;
import com.droid.wiz.toolkits.dialog.LoadingDialog;
import com.droid.wiz.toolkits.lifecycle.DestroyedLifecycleOwner;
import com.droid.wiz.toolkits.net.HttpUtils;
import com.droid.wiz.toolkits.net.request.GetRequest;
import com.droid.wiz.toolkits.net.request.PostRequest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;

/**
 * Created by liuXiao on 07/16/2021
 */
public abstract class BaseFragment extends Fragment {

  protected MReceiver mReceiver;
  protected FragmentActivity mActivity;
  private LoadingDialog mLoadingDialog;
  private boolean mNotResumeDoRefresh;
  private boolean mIsStart;

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    mActivity = (FragmentActivity) context;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initView(savedInstanceState);
  }

  public abstract void initView(@Nullable Bundle savedInstanceState);

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
    return HttpUtils.get(url).bindLifecycle(getViewLifecycleOwner());
  }

  public PostRequest post(@NonNull String url) {
    return HttpUtils.post(url).bindLifecycle(getViewLifecycleOwner());
  }

  public void refresh() {
    if (getView() == null || notActive()) {
      return;
    }
    if (isHidden() || !isResumed()) {
      mNotResumeDoRefresh = true;
      return;
    }
    doRefresh();
  }

  public void doRefresh() {
  }

  private void tryDoRefresh() {
    if (mNotResumeDoRefresh && !isHidden() && isResumed()) {
      mNotResumeDoRefresh = false;
      doRefresh();
    }
  }

  @Override
  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if (!hidden) {
      tryDoRefresh();
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

  public boolean notActive() {
    return mActivity == null || mActivity.isFinishing();
  }

  public boolean isActive() {
    return !notActive();
  }

  public boolean isStart() {
    return mIsStart;
  }

  public void runOnUiThread(Runnable runnable) {
    if (isActive()) {
      mActivity.runOnUiThread(runnable);
    }
  }

  @Override
  public void onStart() {
    super.onStart();
    mIsStart = true;
  }

  @Override
  public void onResume() {
    super.onResume();
    tryDoRefresh();
  }

  @Override
  public void onStop() {
    super.onStop();
    mIsStart = false;
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mActivity = null;
  }

  @NonNull
  @Override
  public LifecycleOwner getViewLifecycleOwner() {
    if (notActive()) {
      return new DestroyedLifecycleOwner();
    }
    try {
      return super.getViewLifecycleOwner();
    } catch (Exception e) {
      return this;
    }
  }
}
