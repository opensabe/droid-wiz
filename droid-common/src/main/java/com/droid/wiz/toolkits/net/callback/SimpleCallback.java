package com.droid.wiz.toolkits.net.callback;

import org.json.JSONObject;

import com.droid.wiz.toolkits.net.exception.ApiException;

import androidx.annotation.NonNull;
import okhttp3.ResponseBody;

public abstract class SimpleCallback extends BaseCallback<Object> {

  public SimpleCallback() {
    super();
  }

  public SimpleCallback(boolean successSendMainThread) {
    super(successSendMainThread);
  }

  @Override
  public Object parserResponse(@NonNull ResponseBody responseBody) throws Exception {
    JSONObject jsonObject = new JSONObject(responseBody.string());
    int bizCode = jsonObject.optInt("bizCode");
    if (bizCode != BaseResp.SUCCESS_CODE) {
      String message = jsonObject.optString("message");
      throw new ApiException(bizCode, message);
    }
    return jsonObject.optString("data");
  }
}
