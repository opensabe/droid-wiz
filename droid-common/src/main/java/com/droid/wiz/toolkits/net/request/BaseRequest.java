package com.droid.wiz.toolkits.net.request;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.net.Uri;
import android.text.TextUtils;

import com.droid.wiz.toolkits.net.HttpUtils;
import com.droid.wiz.toolkits.net.callback.BaseCallback;
import com.droid.wiz.toolkits.net.callback.BaseResp;
import com.droid.wiz.toolkits.net.callback.ExecuteParser;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

@SuppressWarnings("unchecked")
public abstract class BaseRequest<T extends BaseRequest<T>> implements DefaultLifecycleObserver {

  private String mUrl;
  private Call mCall;
  private Lifecycle mLifecycle;
  private Map<String, Object> mHeaderMap;
  private Map<String, Object> mQueryMap;
  private String[] mPathVariables;
  private BaseCallback<?> mBaseCallback;

  public T bindLifecycle(LifecycleOwner owner) {
    if (owner != null) {
      (mLifecycle = owner.getLifecycle()).addObserver(this);
    }
    return (T) this;
  }

  public T url(@NonNull String url) {
    mUrl = url;
    if (!TextUtils.isEmpty(url)) {
      if (!url.startsWith("http") || !url.startsWith("https")) {
        mUrl = HttpUtils.sBaseUrl + url;
      }
    }
    return (T) this;
  }

  public T addHeader(@NonNull String key, Object value) {
    if (mHeaderMap == null) {
      mHeaderMap = new HashMap<>();
    }
    mHeaderMap.put(key, value);
    return (T) this;
  }

  public T setHeaderMap(Map<String, Object> map) {
    mHeaderMap = map;
    return (T) this;
  }

  public T addQuery(@NonNull String key, Object value) {
    if (mQueryMap == null) {
      mQueryMap = new HashMap<>();
    }
    if (value != null) {
      mQueryMap.put(key, value);
    }
    return (T) this;
  }

  public T setQueryMap(Map<String, Object> map) {
    mQueryMap = map;
    return (T) this;
  }

  public T setPathVariables(String... pathVariables) {
    mPathVariables = pathVariables;
    return (T) this;
  }

  public Request createRequest() {
    Request.Builder builder = new Request.Builder();
    initRequest(builder);
    handleRequest(builder);
    return builder.build();
  }

  private void initRequest(Request.Builder builder) {
    if (mHeaderMap != null && !mHeaderMap.isEmpty()) {
      for (Map.Entry<String, Object> entry : mHeaderMap.entrySet()) {
        builder.addHeader(entry.getKey(), entry.getValue() + "");
      }
    }
    if (mQueryMap != null && !mQueryMap.isEmpty()) {
      Uri.Builder uriBuilder = Uri.parse(mUrl).buildUpon();
      for (Map.Entry<String, Object> entry : mQueryMap.entrySet()) {
        uriBuilder.appendQueryParameter(entry.getKey(), entry.getValue() + "");
      }
      mUrl = uriBuilder.build().toString();
    }
    if (mPathVariables != null && mPathVariables.length > 0) {
      Pattern pattern = Pattern.compile("\\{[a-zA-Z0-9]+\\}");
      Matcher matcher = pattern.matcher(mUrl);
      int index = 0;
      while (matcher.find()) {
        String pathVariable = mPathVariables[index];
        mUrl = mUrl.replace(matcher.group(), TextUtils.isEmpty(pathVariable) ? "" : pathVariable);
        index++;
      }
    }
    builder.url(mUrl);
  }

  protected abstract void handleRequest(Request.Builder builder);

  public void enqueue(@NonNull BaseCallback<?> callback) {
    if (mLifecycle != null && mLifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
      return;
    }
    mCall = HttpUtils.newCall(createRequest());
    mBaseCallback = callback;
    mBaseCallback.onStart(mCall);
    mCall.enqueue(new Callback() {
      @Override
      public void onResponse(@NonNull Call call, @NonNull Response response) {
        if (mBaseCallback != null && !call.isCanceled()) {
          mBaseCallback.onResponse(call, response);
        }
      }

      @Override
      public void onFailure(@NonNull Call call, @NonNull IOException e) {
        if (mBaseCallback != null && !call.isCanceled()) {
          mBaseCallback.onFailure(call, e);
        }
      }
    });
  }

  public <D> D execute(@NonNull ExecuteParser<D> executeParser) throws Exception {
    if (mLifecycle != null && mLifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
      return null;
    }
    Call call = HttpUtils.newCall(createRequest());
    ResponseBody body;
    Response response = call.execute();
    if (!response.isSuccessful() || (body = response.body()) == null) {
      return null;
    }
    BaseResp<D> baseResp = executeParser.parser(body.string());
    if (baseResp == null || !baseResp.isSuccess() || baseResp.data == null) {
      return null;
    }
    return baseResp.data;
  }

  @Override
  public void onDestroy(@NonNull LifecycleOwner owner) {
    mBaseCallback = null;
    if (mCall != null) {
      mCall.cancel();
    }
    if (mLifecycle != null) {
      mLifecycle.removeObserver(this);
    }
  }
}
