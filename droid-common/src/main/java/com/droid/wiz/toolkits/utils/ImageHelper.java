package com.droid.wiz.toolkits.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by liuXiao on 07/08/2021
 * ps: 不推荐适合用glide自带加载圆角方法, 如果要实现圆角效果可以使用MImageView
 */
public class ImageHelper {

  public static Context getContext() {
    return com.droid.wiz.toolkits.utils.Tools.getContext();
  }

  public static void load(String url, ImageView imageView) {
    load(url, imageView, -1);
  }

  public static void load(String url, ImageView imageView, @DrawableRes int placeholderResId) {
    RequestBuilder<Drawable> requestBuilder = Glide.with(getContext()).load(url);
    if (placeholderResId != -1) {
      requestBuilder = requestBuilder.placeholder(placeholderResId);
    }
    requestBuilder.into(imageView);
  }

  public static void load(String url, ImageView imageView, @DrawableRes int placeholderResId,
      @DrawableRes int errorResId) {
    RequestBuilder<Drawable> requestBuilder = Glide.with(getContext()).load(url);
    if (placeholderResId != -1) {
      requestBuilder = requestBuilder.placeholder(placeholderResId).error(errorResId);
    }
    requestBuilder.into(imageView);
  }

  public static void loadBlur(String url, ImageView imageView, int radius,
      @DrawableRes int placeholderResId) {
    Glide.with(getContext()).load(url)
        .apply(RequestOptions.bitmapTransform(new BlurTransformation(getContext(), radius)))
        .placeholder(placeholderResId).into(imageView);
  }

  public static void loadBitmap(Context context, String url,
      @NonNull OnLoadListener onLoadListener) {
    Glide.with(context).asBitmap().load(url).into(new CustomTarget<Bitmap>() {
      @Override
      public void onResourceReady(@NonNull Bitmap resource,
          @Nullable Transition<? super Bitmap> transition) {
        onLoadListener.onSuccess(resource);
      }

      @Override
      public void onLoadCleared(@Nullable Drawable placeholder) {
        onLoadListener.onCleared();
      }

      @Override
      public void onLoadFailed(@Nullable Drawable errorDrawable) {
        super.onLoadFailed(errorDrawable);
        onLoadListener.onFailure();
      }
    });
  }

  public interface OnLoadListener {
    void onSuccess(Bitmap bitmap);

    void onFailure();

    default void onCleared() {
    }
  }

  /**
   * 着色
   *
   * @param view       ImageView
   * @param colorResId int
   */
  public static void setImageColor(ImageView view, @ColorRes int colorResId) {

    if (view == null || view.getDrawable() == null) {
      return;
    }
    view.setColorFilter(Tools.getColor(colorResId));
  }
}
