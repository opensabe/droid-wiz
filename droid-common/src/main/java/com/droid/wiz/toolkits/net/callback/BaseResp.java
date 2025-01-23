package com.droid.wiz.toolkits.net.callback;

import androidx.annotation.Keep;

/**
 * Created by liuXiao on 06/29/2021
 */
@Keep
public class BaseResp<T> {

  public static final int SUCCESS_CODE = 10000;
  public static final int DATA_NULL = 10000;
  public int bizCode;
  public String message;
  public T data;

  public boolean isSuccess() {
    return bizCode == SUCCESS_CODE;
  }
}
