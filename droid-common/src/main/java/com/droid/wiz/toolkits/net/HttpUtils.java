package com.droid.wiz.toolkits.net;

import java.util.concurrent.TimeUnit;

import com.droid.wiz.toolkits.net.request.DeleteRequest;
import com.droid.wiz.toolkits.net.request.GetRequest;
import com.droid.wiz.toolkits.net.request.PostRequest;
import com.droid.wiz.toolkits.net.request.PutRequest;
import com.droid.wiz.toolkits.utils.NetworkCaptureUtils;
import com.droid.wiz.toolkits.utils.Tools;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory;

import androidx.annotation.NonNull;
import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class HttpUtils {

  public static String sBaseUrl;
  public static boolean sIsOnline;
  private volatile static HttpUtils mInstance;
  private static Retrofit sRetrofit;
  private final OkHttpClient mOkHttpClient;

  private HttpUtils() {
    if (sBaseUrl == null) {
      throw new UnsupportedOperationException("BaseUrl needs to be initialized first");
    }

    OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS);
    //        .addInterceptor(new CommonHeaderInterceptor()).
    //            addInterceptor(new EncryptionInterceptor())
    //        .addInterceptor(new DecryptionInterceptor());
    if (!Tools.isOnline()) {
      Interceptor networkCaptureInterceptor = NetworkCaptureUtils.getNetworkCaptureInterceptor();
      if (networkCaptureInterceptor != null) {
        builder.addInterceptor(networkCaptureInterceptor);
      }
    }
    //    builder.addInterceptor(new HttpInterceptor());
    mOkHttpClient = builder.build();
  }

  public static HttpUtils getInstance() {
    if (mInstance == null) {
      synchronized (HttpUtils.class) {
        if (mInstance == null) {
          mInstance = new HttpUtils();
        }
      }
    }
    return mInstance;
  }

  public static void initBaseUrl(@NonNull String baseUrlOnline, @NonNull String baseUrlTest,
      boolean isOnline) {
    sIsOnline = isOnline;
    sBaseUrl = isOnline ? baseUrlOnline : baseUrlTest;
  }

  @NonNull
  public static Call newCall(@NonNull Request request) {
    return getOkHttpClient().newCall(request);
  }

  @NonNull
  public static OkHttpClient getOkHttpClient() {
    return getInstance().mOkHttpClient;
  }

  @NonNull
  public static GetRequest get(@NonNull String url) {
    return new GetRequest().url(url);
  }

  @NonNull
  public static PostRequest post(@NonNull String url) {
    return new PostRequest().url(url);
  }

  @NonNull
  public static PutRequest put(@NonNull String url) {
    return new PutRequest().url(url);
  }

  @NonNull
  public static DeleteRequest delete(@NonNull String url) {
    return new DeleteRequest().url(url);
  }

  public static Retrofit getRetrofit() {
    if (sRetrofit == null) {
      sRetrofit = new Retrofit.Builder().baseUrl(sBaseUrl).addConverterFactory(
              MoshiConverterFactory.create(
                  new Moshi.Builder().add(new KotlinJsonAdapterFactory()).build()))
          .client(getOkHttpClient()).build();
    }
    return sRetrofit;
  }
}
