package com.droid.wiz.toolkits.utils;

import java.util.Map;

import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.Nullable;

/**
 * Created by xxb on 07/31/2024
 */
public class UrlTools {
  /**
   * 补充参数到url中，可以处理# ,有相同参数时使用 params中的覆盖原值
   * 添加params参数时不会进行自动编码,如果参数有特殊字符请在调用该方法前进行Url编码
   *
   * @param url    原始url
   * @param params 补充参数
   * @return String
   */
  @Nullable
  public static String addParams(@Nullable String url, @Nullable Map<String, Object> params) {
    if (params == null || params.isEmpty() || com.droid.wiz.toolkits.utils.Tools.isEmpty(url)) {
      return url;
    }
    int index = url.indexOf("#");
    String urlPart1 = url;
    String urlPart2 = null;
    if (index > 0) {
      urlPart1 = url.substring(0, index);
    }
    if (index > 0) {
      urlPart2 = url.substring(index);
    }

    Uri uri = Tools.parse(urlPart1);
    String path = uri.getEncodedPath();
    String scheme = uri.getScheme();
    String host = uri.getHost();
    String query = uri.getEncodedQuery();

    StringBuilder buffer = new StringBuilder();
    buffer.append(scheme);
    buffer.append("://");
    buffer.append(host);
    buffer.append(path);

    // 去掉重复参数，保留
    if (!params.isEmpty()) {
      if (!TextUtils.isEmpty(query)) {
        String[] tParam = query.split("&");
        for (String str : tParam) {
          if (str != null) {
            String[] keyValue = str.split("=");
            if (keyValue.length == 2) {
              if (params.get(keyValue[0]) == null) {
                params.put(keyValue[0], keyValue[1]);
              }
            }
          }
        }
      }
      if (!params.isEmpty()) {
        buffer.append("?");
        // 添加参数
        for (String key : params.keySet()) {
          buffer.append(key);
          buffer.append("=");
          buffer.append(params.get(key));
          buffer.append("&");
        }
        buffer.deleteCharAt(buffer.length() - 1);
      }
    }
    if (urlPart2 != null) {
      buffer.append(urlPart2);
    }
    return buffer.toString();
  }

}
