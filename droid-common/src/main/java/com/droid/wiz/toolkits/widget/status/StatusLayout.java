package com.droid.wiz.toolkits.widget.status;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.droid.wiz.toolkits.R;
import com.droid.wiz.toolkits.net.NetworkUtils;
import com.droid.wiz.toolkits.utils.Tools;

import androidx.annotation.Keep;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by liuXiao on 07/13/2021
 */
@Keep
public class StatusLayout extends FrameLayout {

  private View mLoadingView;
  private View mEmptyView;
  private View mErrorView;
  private OnErrorClickListener mOnErrorClickListener;
  private int mLoadingBgColor = Color.WHITE;
  private int mEmptyBgColor = Color.WHITE;
  private int mErrorBgColor = Color.WHITE;
  private String mEmptyContent;
  private int mInsideHeight;

  public StatusLayout(@NonNull Context context) {
    this(context, null);
  }

  public StatusLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    setVisibility(INVISIBLE);
    setOnClickListener(null);
  }

  public void setLoadingView(@LayoutRes int layoutResId) {
    mLoadingView = View.inflate(getContext(), layoutResId, null);
  }

  public void setLoadingView(View loadingView) {
    mLoadingView = loadingView;
  }

  public void setEmptyView(@LayoutRes int layoutResId) {
    mEmptyView = View.inflate(getContext(), layoutResId, null);
  }

  public void setEmptyView(View emptyView) {
    mEmptyView = emptyView;
  }

  public void setErrorView(@LayoutRes int layoutResId) {
    mErrorView = View.inflate(getContext(), layoutResId, null);
  }

  public void setErrorView(View view) {
    mErrorView = view;
  }

  public void showLoading() {
    removeAllChild();
    if (mLoadingView == null) {
      mLoadingView = View.inflate(getContext(), R.layout.layout_loading, null);
    }
    mLoadingView.setBackgroundColor(mLoadingBgColor);
    addView(mLoadingView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        mInsideHeight > 0 ? mInsideHeight : ViewGroup.LayoutParams.MATCH_PARENT));
    show();
  }

  public boolean isShowLoading() {
    if (getVisibility() != View.VISIBLE) {
      return false;
    }
    return getChildCount() > 0 && getChildAt(0) == mLoadingView;
  }

  public void showEmpty() {
    removeAllChild();
    if (mEmptyView == null) {
      mEmptyView = View.inflate(getContext(), R.layout.layout_empty, null);
      if (Tools.notEmpty(mEmptyContent)) {
        TextView textView = mEmptyView.findViewById(R.id.tv_no_results);
        textView.setText(mEmptyContent);
      }
    }
    mEmptyView.setBackgroundColor(mEmptyBgColor);
    addView(mEmptyView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        mInsideHeight > 0 ? mInsideHeight : ViewGroup.LayoutParams.MATCH_PARENT));
    show();
  }

  public void showError() {
    removeAllChild();
    if (mErrorView == null) {
      mErrorView = View.inflate(getContext(), R.layout.layout_error, null);
      View viewIcon = mErrorView.findViewById(R.id.view_icon);
      TextView tvErrorText = mErrorView.findViewById(R.id.tv_error_text);
      TextView tvErrorTitle = mErrorView.findViewById(R.id.tv_error_title);
      if (NetworkUtils.isNetWorkAvailable(Tools.getContext())) {
        viewIcon.setBackground(Tools.getDrawable(R.drawable.common_data_error));
        tvErrorText.setText(Tools.getString(R.string.common_error_data_text));
        tvErrorTitle.setText(Tools.getString(R.string.common_error_data_title));
      } else {
        viewIcon.setBackground(Tools.getDrawable(R.drawable.common_net_error));
        tvErrorText.setText(Tools.getString(R.string.common_error_net_text));
        tvErrorTitle.setText(Tools.getString(R.string.common_error_net_title));
      }
    }
    mErrorView.setBackgroundColor(mErrorBgColor);
    View retry = mErrorView.findViewById(R.id.viewRetry);
    if (retry != null) {
      retry.setOnClickListener(v -> {
        showLoading();
        if (mOnErrorClickListener != null) {
          mOnErrorClickListener.onErrorClick();
        }
      });
    }
    addView(mErrorView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        mInsideHeight > 0 ? mInsideHeight : ViewGroup.LayoutParams.MATCH_PARENT));
    show();
  }

  public void dismiss() {
    removeAllChild();
    if (getVisibility() == View.VISIBLE) {
      setVisibility(View.INVISIBLE);
    }
  }

  private void removeAllChild() {
    if (getChildCount() > 0) {
      removeAllViews();
    }
  }

  private void show() {
    if (getVisibility() != View.VISIBLE) {
      setVisibility(VISIBLE);
    }
  }

  public void setLoadingBgColor(int loadingBgColor) {
    mLoadingBgColor = loadingBgColor;
  }

  public void setEmptyBgColor(int emptyBgColor) {
    mEmptyBgColor = emptyBgColor;
  }

  public void setErrorBgColor(int errorBgColor) {
    mErrorBgColor = errorBgColor;
  }

  public void setEmptyContent(String emptyContent) {
    this.mEmptyContent = emptyContent;
  }

  public interface OnErrorClickListener {
    void onErrorClick();
  }

  public void setOnErrorClickListener(OnErrorClickListener onErrorClickListener) {
    mOnErrorClickListener = onErrorClickListener;
  }

  public void setInsideHeight(int insideHeight) {
    mInsideHeight = insideHeight;
  }
}
