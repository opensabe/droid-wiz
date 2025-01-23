package com.droid.wiz.toolkits.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.droid.wiz.toolkits.R;
import com.droid.wiz.toolkits.utils.Tools;
import com.droid.wiz.toolkits.widget.CircularProgressView;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

/**
 * Loading弹窗
 */
@Keep
public class LoadingDialog extends Dialog {

  /**
   * 中间的矩形 layout
   */
  private LinearLayout loadingLayout;

  private View view;
  private TextView tipsText;
  private String tips;
  private boolean isCancelable = false;
  private boolean isCancelOutside = false;
  private boolean isBackTouchable = false;
  private final Rect backRect = new Rect(30, 100, 95, 160);

  public LoadingDialog(@NonNull Context context) {
    this(context, R.style.LoadingDialogStyle);
  }

  public LoadingDialog(@NonNull Context context, @StyleRes int themeResId) {
    super(context, themeResId);
    initView();
  }

  private void initView() {
    view = View.inflate(getContext(), R.layout.common_dialog_loading, null);
    loadingLayout = view.findViewById(R.id.dialog_loading);
    tipsText = view.findViewById(R.id.tipTextView);
    tipsText.setGravity(Gravity.CENTER);
    CircularProgressView circularProgressView = view.findViewById(R.id.circularProgressView);
    circularProgressView.setColor(Tools.getColor(R.color.colorAECCFF));
    setCancelOutside(false);
    setCancelable(true);
    super.setContentView(view);
  }

  /**
   * 类似只显示dialog展开状态即阻止用户操作状态,但不显示loading
   */
  public void showOnlyState() {
    if (view != null) {
      view.setVisibility(View.INVISIBLE);
    }
    show();
  }

  @NonNull
  public TextView getTipsTextView() {
    return tipsText;
  }

  @Override
  public void show() {
    try {
      Context context = getContext();
      if (context instanceof Activity && ((Activity) context).isFinishing()) {
        return;
      }
      if (!TextUtils.isEmpty(tips)) {
        tipsText.setText(tips);
      }
      super.setCancelable(isCancelable);
      super.setCanceledOnTouchOutside(isCancelOutside);
      super.show();
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }

  @Override
  public void dismiss() {
    try {
      Context context = getContext();
      if (context instanceof Activity && ((Activity) context).isFinishing()) {
        return;
      }
      super.dismiss();
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }

  /**
   * 设置提示信息
   *
   * @param tips 提示信息
   */
  public void setTips(String tips) {
    this.tips = tips;
  }

  /**
   * 设置是否可以按返回键取消
   *
   * @param isCancelable boolean
   */
  @Override
  public void setCancelable(boolean isCancelable) {
    this.isCancelable = isCancelable;
  }

  /**
   * 设置是否可以取消
   *
   * @param isCancelOutside boolean
   */
  public void setCancelOutside(boolean isCancelOutside) {
    this.isCancelOutside = isCancelOutside;
  }

  public void setBackTouchable(boolean isBackTouchable) {
    this.isBackTouchable = isBackTouchable;
  }

  @Override
  public boolean onTouchEvent(@NonNull MotionEvent event) {
    if (isBackTouchable && backRect != null && event.getAction() == MotionEvent.ACTION_DOWN) {
      if (event.getX() >= backRect.left && event.getX() <= backRect.right &&
          event.getY() >= backRect.top && event.getY() <= backRect.bottom) {
        this.dismiss();
      }
    }
    return super.onTouchEvent(event);
  }

  /**
   * @param width  int dip 若值为0，则为默认值
   * @param height int dip 若值为0，则为默认值
   */
  public void setDialogWidthAndHeight(int width, int height) {
    ViewGroup.LayoutParams layoutParams = loadingLayout.getLayoutParams();
    if (width != 0) {
      layoutParams.width = Tools.dp2px(width);
    }
    if (height != 0) {
      layoutParams.height = Tools.dp2px(height);
    }
    loadingLayout.setLayoutParams(layoutParams);
  }

  public void setTextVisible(boolean flag) {
    if (tipsText != null) {
      tipsText.setVisibility(flag ? View.VISIBLE : View.GONE);
    }
  }

}
