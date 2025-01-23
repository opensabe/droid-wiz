package com.droid.wiz.toolkits.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.droid.wiz.toolkits.R;

/**
 * Created by liuXiao on 10/13/2021
 * 从底部进入Dialog
 */
public class BottomInDialog extends BottomSheetDialog {

  private final int mHeight;

  public BottomInDialog(Context context) {
    this(context, 0);
  }

  public BottomInDialog(Context context, int height) {
    super(context, R.style.BottomSheetDialog);
    mHeight = height;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    View bottomSheetView = findViewById(com.google.android.material.R.id.design_bottom_sheet);
    if (bottomSheetView != null) {
      BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheetView);
      behavior.setSkipCollapsed(true);
      behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
    if (mHeight > 0 && getWindow() != null) {
      getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, mHeight);
      getWindow().setGravity(Gravity.BOTTOM);
    }
  }
}
