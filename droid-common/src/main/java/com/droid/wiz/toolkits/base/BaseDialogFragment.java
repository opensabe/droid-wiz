package com.droid.wiz.toolkits.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.droid.wiz.toolkits.dialog.LoadingDialog;
import com.droid.wiz.toolkits.utils.Tools;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewbinding.ViewBinding;

/**
 * Created by liuXiao on 11/05/2021
 */
@SuppressWarnings("all")
public abstract class BaseDialogFragment<V extends ViewBinding> extends DialogFragment {

  public static int WRAP_CONTENT = WindowManager.LayoutParams.WRAP_CONTENT;
  public static int MATCH_PARENT = WindowManager.LayoutParams.MATCH_PARENT;

  private int mWidth = WRAP_CONTENT; //宽
  private int mHeight = WRAP_CONTENT; //高
  private float mDimAmount = 0.5f; //蒙层透明度
  private int mGravity = Gravity.CENTER; //弹框位置
  private int mX, mY; //x,y
  private int mWindowAnimations; //动画
  protected Activity mActivity;
  private LoadingDialog mLoadingDialog;
  protected V mBinding;

  public BaseDialogFragment setWidth(int width) {
    mWidth = width;
    return this;
  }

  public BaseDialogFragment setHeight(int height) {
    mHeight = height;
    return this;
  }

  public BaseDialogFragment setDimAmount(float dimAmount) {
    mDimAmount = dimAmount;
    return this;
  }

  public BaseDialogFragment setGravity(int gravity) {
    mGravity = gravity;
    return this;
  }

  public BaseDialogFragment setXY(int x, int y) {
    mX = x;
    mY = y;
    return this;
  }

  public BaseDialogFragment setWindowAnimations(int windowAnimations) {
    mWindowAnimations = windowAnimations;
    return this;
  }

  /**
   * 设置完属性后,必须调用本方法属性才会生效
   */
  public void setUp() {
    if (getDialog() != null) {
      Window window = getDialog().getWindow();
      if (window != null) {
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = mWidth;
        attributes.height = mHeight;
        attributes.dimAmount = mDimAmount;
        attributes.gravity = mGravity;
        attributes.x = mX;
        attributes.y = mY;
        if (mWindowAnimations != 0) {
          attributes.windowAnimations = mWindowAnimations;
        }
        window.setAttributes(attributes);
      }
    }
  }

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    mActivity = (Activity) context;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    mBinding = createViewBinding(inflater, container);
    return mBinding.getRoot();
  }

  protected abstract V createViewBinding(LayoutInflater inflater, ViewGroup container);

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initView(savedInstanceState);
  }

  public abstract void initView(@Nullable Bundle savedInstanceState);

  public void show(@NonNull Context context) {
    try {
      Activity activity = Tools.getActivityByContext(context);
      if (activity == null) {
        return;
      }
      if (activity instanceof FragmentActivity) {
        FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
        super.show(fragmentManager, getClass().getSimpleName());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void dismiss() {
    try {
      super.dismiss();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected void showLoadingDialog() {
    if (mLoadingDialog == null) {
      mLoadingDialog = new LoadingDialog(mActivity);
    }
    mLoadingDialog.show();
  }

  protected void dismissLoadingDialog() {
    if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
      mLoadingDialog.dismiss();
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mBinding = null;
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mActivity = null;
  }
}
