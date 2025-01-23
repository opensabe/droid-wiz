package com.droid.wiz.toolkits.net.callback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

import androidx.annotation.NonNull;
import okhttp3.ResponseBody;

public abstract class CustomCallback<T> extends BaseCallback<T> {

  public CustomCallback() {
    super();
  }

  public CustomCallback(boolean successSendMainThread) {
    super(successSendMainThread);
  }

  @Override
  public T parserResponse(@NonNull ResponseBody responseBody) throws Exception {
    Type[] types = ((ParameterizedType) Objects.requireNonNull(
        getClass().getGenericSuperclass())).getActualTypeArguments();
    T t = GSON.fromJson(responseBody.string(), types[0]);
    if (t == null) {
      throw new Exception();
    }
    return t;
  }
}
