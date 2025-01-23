package com.droid.wiz.toolkits.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

/**
 * Created by liuXiao on 07/15/2021
 */
public abstract class BaseVBActivity<V extends ViewBinding> extends BaseActivity {

  protected V mBinding;

  @NonNull
  protected abstract V createViewBinding();

  @CallSuper
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = createViewBinding();
    super.setContentView(mBinding.getRoot());
    initView(savedInstanceState);
  }

  public abstract void initView(@Nullable Bundle bundle);

  @Override
  public void setContentView(int layoutResID) {
    //super.setContentView(layoutResID);
  }

  @Override
  public void setContentView(View view) {
    //super.setContentView(view);
  }

  @Override
  public void setContentView(View view, ViewGroup.LayoutParams params) {
    //super.setContentView(view, params);
  }

  public V getBinding() {
    return mBinding;
  }
}
