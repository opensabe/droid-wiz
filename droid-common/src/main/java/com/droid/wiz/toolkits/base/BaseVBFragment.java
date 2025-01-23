package com.droid.wiz.toolkits.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

/**
 * Created by liuXiao on 07/16/2021
 */
public abstract class BaseVBFragment<V extends ViewBinding> extends BaseFragment {

  protected V mBinding;

  public abstract @NonNull V createViewBinding(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup container);

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    mBinding = createViewBinding(inflater, container);
    return mBinding.getRoot();
  }

  @Override
  public boolean isActive() {
    if (mBinding == null) {
      return false;
    }
    return super.isActive();
  }

  @Override
  public void runOnUiThread(Runnable runnable) {
    super.runOnUiThread(() -> {
      if (mBinding != null) {
        runnable.run();
      }
    });
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mBinding = null;
  }

  public V getBinding() {
    return mBinding;
  }
}
