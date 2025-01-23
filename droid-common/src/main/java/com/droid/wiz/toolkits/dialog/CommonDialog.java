package com.droid.wiz.toolkits.dialog;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import com.droid.wiz.toolkits.base.BaseDialog;
import com.droid.wiz.toolkits.databinding.DialogCommonBinding;
import com.droid.wiz.toolkits.utils.Tools;

import androidx.annotation.StringRes;

/**
 * Created by liuXiao on 10/25/2021
 */
public class CommonDialog extends BaseDialog {
  public CommonDialog(Context context) {
    super(context);
    int width = Tools.getScreenWidth() - Tools.dp2px(48 * 2);
    setWidth(width).setUp();
  }

  public static class Builder {
    private final Context mContext;
    private String mTitle;
    private SpannableStringBuilder mContent;
    private String mLeftText;
    private String mRightText;
    private boolean mIsCloseVisible;
    private boolean mCancelable = true;
    private Integer mBgColor;
    private Float mDimAmount;
    private OnDialogClickListener mLeftClickListener;
    private OnDialogClickListener mRightClickListener;

    public Builder(Context context) {
      mContext = context;
    }

    public Builder setTitle(String title) {
      mTitle = title;
      return this;
    }

    public Builder setTitle(@StringRes int resId) {
      mTitle = Tools.getString(resId);
      return this;
    }

    public Builder setContent(String content) {
      mContent = SpannableStringBuilder.valueOf(content);
      return this;
    }

    public Builder setContent(@StringRes int resId) {
      mContent = SpannableStringBuilder.valueOf(Tools.getString(resId));
      return this;
    }

    public Builder setContent(SpannableStringBuilder stringBuilder) {
      mContent = stringBuilder;
      return this;
    }

    public Builder setLeftBtn(String leftText, OnDialogClickListener leftClickListener) {
      mLeftText = leftText;
      mLeftClickListener = leftClickListener;
      return this;
    }

    public Builder setRightBtn(String rightText, OnDialogClickListener leftClickListener) {
      mRightText = rightText;
      mRightClickListener = leftClickListener;
      return this;
    }

    public Builder setLeftBtn(@StringRes int resId, OnDialogClickListener clickListener) {
      mLeftText = Tools.getString(resId);
      mLeftClickListener = clickListener;
      return this;
    }

    public Builder setRightBtn(@StringRes int resId, OnDialogClickListener clickListener) {
      mRightText = Tools.getString(resId);
      mRightClickListener = clickListener;
      return this;
    }

    public Builder setCloseVisible(boolean isCloseVisible) {
      mIsCloseVisible = isCloseVisible;
      return this;
    }

    public Builder setCancelable(boolean cancelable) {
      mCancelable = cancelable;
      return this;
    }

    public Builder setBgColor(int bgColor) {
      mBgColor = bgColor;
      return this;
    }

    public Builder setDimAmount(float dimAmount) {
      mDimAmount = dimAmount;
      return this;
    }

    public CommonDialog build() {
      CommonDialog commonDialog = new CommonDialog(mContext);
      commonDialog.setCancelable(mCancelable);
      DialogCommonBinding binding = DialogCommonBinding.inflate(commonDialog.getLayoutInflater());
      commonDialog.setContentView(binding.getRoot());
      if (mBgColor != null) {
        binding.getRoot().setNormalColor(mBgColor);
      }
      if (mDimAmount != null) {
        commonDialog.setDimAmount(mDimAmount);
        commonDialog.setUp();
      }
      binding.ivClose.setVisibility(mIsCloseVisible ? View.VISIBLE : View.INVISIBLE);
      setTextUI(binding.tvTitle, mTitle);
      setTextUI(binding.tvContent, mContent);
      setTextUI(binding.tvLeft, mLeftText);
      setTextUI(binding.tvRight, mRightText);
      binding.tvLeft.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (mLeftClickListener != null) {
            mLeftClickListener.onClickListener(commonDialog);
          }
        }
      });
      binding.tvRight.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (mRightClickListener != null) {
            mRightClickListener.onClickListener(commonDialog);
          }
        }
      });
      binding.ivClose.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          commonDialog.dismiss();
        }
      });
      return commonDialog;
    }

    private void setTextUI(TextView textView, CharSequence text) {
      if (Tools.isEmpty(text)) {
        textView.setVisibility(View.GONE);
      } else {
        textView.setVisibility(View.VISIBLE);
        textView.setText(text);
      }
    }
  }

  public interface OnDialogClickListener {
    void onClickListener(CommonDialog dialog);
  }
}
