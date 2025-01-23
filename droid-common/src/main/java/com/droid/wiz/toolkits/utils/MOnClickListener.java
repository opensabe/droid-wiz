package com.droid.wiz.toolkits.utils;

import android.view.View;

/**
 * Created by fangzheng on 12/22/2020
 */
public abstract class MOnClickListener implements View.OnClickListener {
  @Override
  public void onClick(View v) {
    if (ClickUtil.isValid(v.getId())) {
      onValidClick(v);
    }
  }

  /**
   * 有效点击事件(距离上次点击事件的间隔大于1000ms)
   *
   * @param v View
   */
  public abstract void onValidClick(View v);
}
