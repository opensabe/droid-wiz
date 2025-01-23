package com.droid.wiz.toolkits.utils;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by liuXiao on 11/08/2021
 */
public class GsonUtils {

  private static final Gson sGson = new Gson();

  public static String toJson(Object object) {
    try {
      return sGson.toJson(object);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }

  public static <T> T fromJson(String json, Class<T> t) {
    return sGson.fromJson(json, t);
  }

  public static <T> T fromJson(String json, Type type) {
    return sGson.fromJson(json, type);
  }

  public static <T> T fromJson(JsonElement json, Type type) {
    return sGson.fromJson(json, type);
  }

  @Nullable
  public static String optString(@NonNull JsonObject o, @NonNull String key) {
    JsonElement e = o.get(key);
    if (e != null && e.isJsonPrimitive()) {
      return e.getAsString();
    }
    return null;
  }

}
