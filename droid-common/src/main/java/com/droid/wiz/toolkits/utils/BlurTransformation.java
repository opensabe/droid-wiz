package com.droid.wiz.toolkits.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.renderscript.RSRuntimeException;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import androidx.annotation.NonNull;
import jp.wasabeef.glide.transformations.internal.FastBlur;
import jp.wasabeef.glide.transformations.internal.RSBlur;


/**
 * 虚化Transformation
 * 更多效果参考：<a href="https://github.com/wasabeef/glide-transformations">...</a>
 */

@SuppressWarnings("unused")
public class BlurTransformation extends BitmapTransformation {

  private static final String STRING_CHARSET_NAME = "UTF-8";
  private static final String ID = "com.tenn.credit.common.utils.BlurTransformation";
  private static final Charset CHARSET = Charset.forName(STRING_CHARSET_NAME);
  private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

  private static final int DEF_TOP_MARGIN = Tools.dp2px(10);
  private static final int MAX_RADIUS = 25;
  private static final int DEFAULT_DOWN_SAMPLING = 1;

  private final Context mContext;
  private final BitmapPool mBitmapPool;

  private final int mRadius;
  private final int mSampling;

  public BlurTransformation(Context context) {
    this(context, Glide.get(context).getBitmapPool(), MAX_RADIUS, DEFAULT_DOWN_SAMPLING);
  }

  public BlurTransformation(Context context, BitmapPool pool) {
    this(context, pool, MAX_RADIUS, DEFAULT_DOWN_SAMPLING);
  }

  public BlurTransformation(Context context, BitmapPool pool, int radius) {
    this(context, pool, radius, DEFAULT_DOWN_SAMPLING);
  }

  public BlurTransformation(Context context, int radius) {
    this(context, Glide.get(context).getBitmapPool(), radius, DEFAULT_DOWN_SAMPLING);
  }

  public BlurTransformation(Context context, int radius, int sampling) {
    this(context, Glide.get(context).getBitmapPool(), radius, sampling);
  }

  public BlurTransformation(Context context, BitmapPool pool, int radius, int sampling) {
    mContext = context.getApplicationContext();
    mBitmapPool = pool;
    mRadius = radius;
    mSampling = sampling;
  }


  @Override
  protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth,
      int outHeight) {
    int width = toTransform.getWidth();
    int height = toTransform.getHeight();
    int scaledWidth = width / mSampling;
    int scaledHeight = height / mSampling;
    Bitmap bitmap = mBitmapPool.get(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    canvas.scale(1 / (float) mSampling, 1 / (float) mSampling);
    Paint paint = new Paint();
    paint.setFlags(Paint.FILTER_BITMAP_FLAG);
    canvas.drawBitmap(toTransform, 0, DEF_TOP_MARGIN, paint);
    try {
      bitmap = RSBlur.blur(mContext, bitmap, mRadius);
    } catch (RSRuntimeException e) {
      bitmap = FastBlur.blur(bitmap, mRadius, true);
    }
    return bitmap;
  }

  @Override
  public int hashCode() {
    return ID.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof BlurTransformation;
  }

  @Override
  public void updateDiskCacheKey(MessageDigest messageDigest) {
    messageDigest.update(ID_BYTES);
  }
}
