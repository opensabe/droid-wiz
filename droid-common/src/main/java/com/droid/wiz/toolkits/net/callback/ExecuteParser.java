package com.droid.wiz.toolkits.net.callback;

import java.lang.reflect.ParameterizedType;

import com.droid.wiz.toolkits.utils.GsonUtils;

public abstract class ExecuteParser<T> {

  public com.droid.wiz.toolkits.net.callback.BaseResp<T> parser(String json) {
    ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
    if (genericSuperclass != null) {
      return GsonUtils.fromJson(json,
          new ParameterizedTypeImpl(BaseResp.class, genericSuperclass.getActualTypeArguments()));
    }
    return null;
  }
}
