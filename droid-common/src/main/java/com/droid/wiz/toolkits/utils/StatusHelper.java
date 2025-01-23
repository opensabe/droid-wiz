package com.droid.wiz.toolkits.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.droid.wiz.toolkits.R;
import com.droid.wiz.toolkits.base.BaseActivity;
import com.droid.wiz.toolkits.dialog.LoadingDialog;
import com.droid.wiz.toolkits.widget.status.StatusLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;

/**
 * @author zn
 * description : 页面状态辅助类，LoadingDialog，loading，empty,error等状态
 * date : 2021/10/27
 */
public class StatusHelper {

  public static final int STATUS_LOADING = 1;
  public static final int STATUS_ERROR = 2;
  public static final int STATUS_EMPTY = 3;
  public static final int STATUS_NONE = 0;

  /**
   * 简化loading dialog 使用
   *
   * @param activity 仅支持继承BaseActivity的activity
   */
  public static void showLoadingDialog(Activity activity) {
    showLoadingDialog(activity, com.droid.wiz.toolkits.utils.Tools.getString(R.string.loading));
  }

  public static void showLoadingDialog(Activity activity, String tips) {
    if (activity == null) {
      return;
    }
    if (!(activity instanceof BaseActivity)) {
      return;
    }
    BaseActivity baseActivity = (BaseActivity) activity;
    if (baseActivity.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
      return;
    }
    if (baseActivity.mLoadingDialog == null) {
      baseActivity.mLoadingDialog = new LoadingDialog(activity);
    }
    if (baseActivity.mLoadingDialog.isShowing()) {
      return;
    }
    baseActivity.mLoadingDialog.setTips(tips);
    baseActivity.mLoadingDialog.show();
  }

  public static void dismissLoadingDialog(Activity activity) {
    if (activity == null) {
      return;
    }
    if (!(activity instanceof BaseActivity)) {
      return;
    }
    BaseActivity baseActivity = (BaseActivity) activity;
    LoadingDialog loadingDialog = baseActivity.mLoadingDialog;
    if (baseActivity.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED ||
        loadingDialog == null || !loadingDialog.isShowing()) {
      return;
    }
    loadingDialog.dismiss();
  }


  /**
   * loading
   *
   * @param parent 需要展示loading的ViewGroup ，不建议使用带方向的LinearLayout
   */
  public static void showLoadingStatus(ViewGroup parent) {
    showStatus(parent, STATUS_LOADING);
  }

  /**
   * empty
   *
   * @param parent 需要展示empty的ViewGroup
   */
  public static void showEmptyStatus(ViewGroup parent) {
    showStatus(parent, STATUS_EMPTY);
  }

  /**
   * 移除loading
   *
   * @param parent 需要移除loading，empty，error状态的ViewGroup
   */
  public static void dismissStatus(ViewGroup parent) {
    showStatus(parent, STATUS_NONE);
  }

  /**
   * Error状态独立方法
   *
   * @param parent   状态父布局
   * @param listener error 按钮点击回调
   */
  public static void showErrorStatus(ViewGroup parent, StatusLayout.OnErrorClickListener listener) {
    if (parent == null) {
      return;
    }
    StatusLayout layout = getStatusLayout(parent);
    layout.setOnErrorClickListener(listener);
    layout.showError();
  }

  /**
   * status状态，loading，empty，error,none
   *
   * @param parent 状态父布局
   * @param status 状态
   */
  public static void showStatus(ViewGroup parent, int status) {
    if (parent == null) {
      return;
    }
    StatusLayout layout = getStatusLayout(parent);
    switch (status) {
      case STATUS_LOADING:
        layout.setLoadingBgColor(Tools.getColor(R.color.white));
        layout.showLoading();
        break;
      case STATUS_EMPTY:
        layout.showEmpty();
        break;
      case STATUS_ERROR:
        //请直接使用showError
        showErrorStatus(parent, null);
        break;
      case STATUS_NONE:
      default:
        for (int i = 0; i < parent.getChildCount(); i++) {
          View view = parent.getChildAt(i);
          if (view instanceof StatusLayout) {
            ((StatusLayout) view).dismiss();
          }
        }
        break;
    }
  }

  @NonNull
  public static StatusLayout getStatusLayout(ViewGroup parent) {
    StatusLayout layout = null;
    if (parent.getChildCount() > 0) {
      for (int i = 0; i < parent.getChildCount(); i++) {
        View view = parent.getChildAt(i);
        if (view instanceof StatusLayout) {
          if (i < parent.getChildCount() - 1) {
            parent.removeView(view);
          } else {
            layout = (StatusLayout) view;
          }
        }
      }
    }

    if (layout == null) {
      layout = new StatusLayout(parent.getContext());
      ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
      parent.addView(layout, params);
    }
    return layout;
  }

}
