package com.droid.wiz.toolkits.net.request;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.text.TextUtils;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

@SuppressWarnings("unchecked")
public abstract class CommonRequest<T extends com.droid.wiz.toolkits.net.request.BaseRequest<T>> extends
    BaseRequest<T> {

  public static final int POST = 1;
  public static final int PUT = 2;
  public static final int DELETE = 3;

  @IntDef({ POST, PUT, DELETE })
  @Retention(RetentionPolicy.SOURCE)
  public @interface RequestType {

  }

  public static MediaType JSON = MediaType.parse("application/json; charset=utf-8");
  private final int mRequestType;
  private Map<String, Object> mFieldMap;
  private List<com.droid.wiz.toolkits.net.request.FileEntity> mFileList;
  private String mJson;

  public CommonRequest(@RequestType int requestType) {
    mRequestType = requestType;
  }

  public T addField(@NonNull String key, Object value) {
    if (mFieldMap == null) {
      mFieldMap = new HashMap<>();
    }
    mFieldMap.put(key, value + "");
    return (T) this;
  }

  public T setFieldMap(Map<String, Object> map) {
    mFieldMap = map;
    return (T) this;
  }

  public T addFile(com.droid.wiz.toolkits.net.request.FileEntity fileEntity) {
    if (mFileList == null) {
      mFileList = new ArrayList<>();
    }
    mFileList.add(fileEntity);
    return (T) this;
  }

  public T setFileList(List<com.droid.wiz.toolkits.net.request.FileEntity> list) {
    mFileList = list;
    return (T) this;
  }

  public T setJson(String json) {
    mJson = json;
    return (T) this;
  }

  @Override
  protected void handleRequest(Request.Builder builder) {
    RequestBody requestBody;
    if (!TextUtils.isEmpty(mJson)) {
      requestBody = RequestBody.create(JSON, mJson);
    } else {
      if (mFileList != null && mFileList.size() > 0) {
        requestBody = createMultipartBody();
      } else {
        requestBody = createFormBody();
      }
    }
    switch (mRequestType) {
      case PUT:
        builder.put(requestBody);
        break;
      case DELETE:
        builder.delete(requestBody);
        break;
      default:
        builder.post(requestBody);
        break;
    }
  }

  private MultipartBody createMultipartBody() {
    MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
    if (mFieldMap != null && !mFieldMap.isEmpty()) {
      for (Map.Entry<String, Object> entry : mFieldMap.entrySet()) {
        builder.addFormDataPart(entry.getKey(), entry.getValue() + "");
      }
    }
    if (mFileList != null && !mFileList.isEmpty()) {
      for (FileEntity entity : mFileList) {
        if (entity == null) {
          continue;
        }
        File file = new File(entity.path);
        builder.addFormDataPart(entity.key, file.getName(),
            RequestBody.create(MediaType.parse(entity.mediaType), file));
      }
    }
    return builder.build();
  }

  private RequestBody createFormBody() {
    FormBody.Builder builder = new FormBody.Builder();
    if (mFieldMap != null && !mFieldMap.isEmpty()) {
      for (Map.Entry<String, Object> entry : mFieldMap.entrySet()) {
        builder.add(entry.getKey(), entry.getValue() + "");
      }
    }
    return builder.build();
  }
}
