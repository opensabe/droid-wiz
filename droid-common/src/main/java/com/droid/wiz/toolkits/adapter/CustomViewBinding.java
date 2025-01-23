package com.droid.wiz.toolkits.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

/**
 * Created by liuXiao on 10/15/2021
 */
public class CustomViewBinding implements ViewBinding {

  private final View mView;

  public CustomViewBinding(View view) {
    mView = view;
  }

  @NonNull
  @Override
  public View getRoot() {
    return mView;
  }
}
