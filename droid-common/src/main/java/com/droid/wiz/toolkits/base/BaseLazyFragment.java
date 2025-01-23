package com.droid.wiz.toolkits.base;

import androidx.viewbinding.ViewBinding;

/**
 * Created by liuXiao on 10/18/2021
 * 懒加载Fragment
 */
public abstract class BaseLazyFragment<V extends ViewBinding> extends BaseVBFragment<V> {

  private boolean mIsFirstLoad = true;

  @Override
  public void onResume() {
    super.onResume();
    if (mIsFirstLoad) {
      onLazyLoad();
      mIsFirstLoad = false;
    }
  }

  public abstract void onLazyLoad();

}
