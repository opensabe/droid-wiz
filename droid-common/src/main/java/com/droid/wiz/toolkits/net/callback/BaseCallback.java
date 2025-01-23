package com.droid.wiz.toolkits.net.callback;

import java.io.IOException;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.droid.wiz.toolkits.R;
import com.droid.wiz.toolkits.net.exception.ApiException;
import com.droid.wiz.toolkits.net.exception.DataNullException;
import com.droid.wiz.toolkits.net.exception.LoginInvalidException;
import com.droid.wiz.toolkits.utils.Tools;

import androidx.annotation.NonNull;
import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class BaseCallback<T> {

  public static final String TAG = "BaseCallback";
  public static final int LOGIN_INVALID_CODE = 401;
  public static final int FAILURE_CODE = Integer.MIN_VALUE;
  public static final int DATA_NULL_CODE = FAILURE_CODE + 1;
  public static final Handler HANDLER = new Handler(Looper.getMainLooper());
  public static final Gson GSON = new Gson();
  private final boolean successSendMainThread;
  public Call mOKHttpCall;

  public BaseCallback() {
    successSendMainThread = true;
  }

  /**
   * 当successSendMainThread参数为false时, onSuccess会运行在子线程
   */
  public BaseCallback(boolean successSendMainThread) {
    this.successSendMainThread = successSendMainThread;
  }

  public void onStart(Call call) {
    this.mOKHttpCall = call;
  }

  public void onResponse(@NonNull Call call, @NonNull Response response) {
    try {
      ResponseBody responseBody;
      if (!response.isSuccessful() || (responseBody = response.body()) == null) {
        throw new Exception();
      }
      handleSuccess(call, parserResponse(responseBody));
    } catch (Exception e) {
      if (e instanceof ApiException) {
        handleFailure(call, ((ApiException) e).code, ((ApiException) e).msg);
      } else if (e instanceof DataNullException) {
        handleFailure(call, DATA_NULL_CODE, Tools.getString(R.string.abnormal));
      } else {
        handleFailure(call, FAILURE_CODE, Tools.getString(R.string.abnormal));
      }
    }
  }

  public void onFailure(@NonNull Call call, @NonNull IOException e) {
    String msg = Tools.getString(R.string.net_error_tips);
    if (e instanceof LoginInvalidException) {
      msg = "";
    }
    handleFailure(call, FAILURE_CODE, msg);
  }

  private void handleSuccess(Call call, @NonNull T data) {
    if (!successSendMainThread) {
      onSuccess(data);
      return;
    }
    HANDLER.post(() -> {
      if (isCanceled(call)) {
        return;
      }
      onSuccess(data);
    });
  }

  private void handleFailure(Call call, int code, String msg) {
    HANDLER.post(() -> {
      if (isCanceled(call)) {
        return;
      }
      onFailure(code, msg);
    });
  }

  public boolean isCanceled(Call call) {
    return call != null && call.isCanceled();
  }

  public abstract T parserResponse(@NonNull ResponseBody responseBody) throws Exception;

  public abstract void onSuccess(@NonNull T data);

  public abstract void onFailure(int code, String msg);
}
