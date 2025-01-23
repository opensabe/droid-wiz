package com.droid.wiz.toolkits.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.droid.wiz.toolkits.R;
import com.droid.wiz.toolkits.utils.Tools;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewbinding.ViewBinding;

/**
 * Created by liuXiao on 11/08/2021
 */
public abstract class BottomInDialogFragment<V extends ViewBinding> extends
    BottomSheetDialogFragment {

  protected Activity mActivity;
  private com.droid.wiz.toolkits.dialog.LoadingDialog mLoadingDialog;
  private int mHeight;
  protected V mBinding;

  public abstract @NonNull
  V createViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    mActivity = (Activity) context;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(STYLE_NORMAL, R.style.BottomSheetDialog);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    mBinding = createViewBinding(inflater, container);
    return mBinding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    BottomSheetBehavior<View> behavior = BottomSheetBehavior.from((View) view.getParent());
    behavior.setSkipCollapsed(true);
    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
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

  public void setHeight(int height) {
    mHeight = height;
  }

  @Override
  public void onStart() {
    super.onStart();
    if (mHeight > 0 && getDialog() != null && getDialog().getWindow() != null) {
      getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, mHeight);
      getDialog().getWindow().setGravity(Gravity.BOTTOM);
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
