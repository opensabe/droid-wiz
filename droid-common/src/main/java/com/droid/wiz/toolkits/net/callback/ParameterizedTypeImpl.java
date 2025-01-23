package com.droid.wiz.toolkits.net.callback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.NonNull;

public class ParameterizedTypeImpl implements ParameterizedType {

  private final Type mRawType;
  private final Type[] mTypes;

  public ParameterizedTypeImpl(Type rawType, Type[] types) {
    mRawType = rawType;
    this.mTypes = types != null ? types : new Type[0];
  }

  @NonNull
  @Override
  public Type[] getActualTypeArguments() {
    return mTypes;
  }

  @NonNull
  @Override
  public Type getRawType() {
    return mRawType;
  }

  @Override
  public Type getOwnerType() {
    return null;
  }
}