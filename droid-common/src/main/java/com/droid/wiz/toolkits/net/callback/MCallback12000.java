package com.droid.wiz.toolkits.net.callback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

import com.droid.wiz.toolkits.net.exception.ApiException;
import com.droid.wiz.toolkits.net.exception.DataNullException;

import androidx.annotation.NonNull;
import okhttp3.ResponseBody;

public abstract class MCallback12000<T> extends
    BaseCallback<com.droid.wiz.toolkits.net.callback.BaseResp<T>> {

  public MCallback12000() {
    super();
  }

  public MCallback12000(boolean successSendMainThread) {
    super(successSendMainThread);
  }

  @Override
  public com.droid.wiz.toolkits.net.callback.BaseResp<T> parserResponse(@NonNull ResponseBody responseBody) throws Exception {
    Type[] types = ((ParameterizedType) Objects.requireNonNull(
        getClass().getGenericSuperclass())).getActualTypeArguments();
    com.droid.wiz.toolkits.net.callback.BaseResp<T> resp =
        GSON.fromJson(responseBody.string(), new ParameterizedTypeImpl(BaseResp.class, types));
    if (resp == null) {
      throw new Exception();
    }
    if (resp.bizCode != 10000 && resp.bizCode != 12000) {
      throw  new ApiException(resp.bizCode, resp.message);
    }
    if (resp.bizCode == 12000 && resp.data == null) {
      throw  new ApiException(resp.bizCode, resp.message);
    }
    if (resp.data == null) {
      throw new DataNullException();
    }
    return resp;
  }
}
