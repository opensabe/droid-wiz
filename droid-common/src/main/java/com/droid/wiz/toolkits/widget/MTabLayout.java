package com.droid.wiz.toolkits.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.droid.wiz.toolkits.R;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

/**
 * Created by liuXiao on 10/18/2021
 * 重写 TabLayout, 自定义下划线
 */
@Keep
public class MTabLayout extends TabLayout {

  private static final long DURATION = 200L;

  private int mIndicatorColor;
  private int mIndicatorWidth;
  private int mIndicatorHeight;
  private int mIndicatorMargin;
  private int mIndicatorCorner;
  private Bitmap mIndicatorBitmap;
  private final RectF mRectF = new RectF();
  private final Paint mPaint = new Paint();
  private ViewPager2 mViewPager;
  private TabLayoutMediator mTabLayoutMediator;
  private final LinearInterpolator mInterpolator = new LinearInterpolator();

  public MTabLayout(@NonNull Context context) {
    this(context, null);
  }

  public MTabLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    initTypedArray(attrs);
    initPaint();
    setSelectedTabIndicator(null);
    addOnTabSelectedListener(new MOnTabSelectedListener() {
      @Override
      public void onTabSelected(Tab tab) {
        super.onTabSelected(tab);
        if (mViewPager == null) {
          calculationRectF(false);
        }
      }
    });
  }

  private void initTypedArray(AttributeSet attrs) {
    TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MTabLayout);
    mIndicatorColor = a.getColor(R.styleable.MTabLayout_mIndicatorColor, Color.BLACK);
    mIndicatorWidth = a.getDimensionPixelOffset(R.styleable.MTabLayout_mIndicatorWidth, 0);
    mIndicatorHeight = a.getDimensionPixelOffset(R.styleable.MTabLayout_mIndicatorHeight, 0);
    mIndicatorMargin = a.getDimensionPixelOffset(R.styleable.MTabLayout_mIndicatorMargin, 0);
    mIndicatorCorner = a.getDimensionPixelOffset(R.styleable.MTabLayout_mIndicatorCorner, 0);
    int resourceId = a.getResourceId(R.styleable.MTabLayout_mIndicatorResId, 0);
    if (resourceId != 0) {
      Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
      if (mIndicatorWidth == 0 || mIndicatorHeight == 0) {
        mIndicatorBitmap = bitmap;
      } else {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = ((float) mIndicatorWidth) / width;
        float scaleHeight = ((float) mIndicatorHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        mIndicatorBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
      }
    }
    a.recycle();
  }

  private void initPaint() {
    mPaint.setAntiAlias(true);
    mPaint.setColor(mIndicatorColor);
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);
    if (!changed && mRectF.height() > 0) {
      return;
    }
    Tab tabAt = getTabAt(0);
    if (tabAt != null) {
      TabView view = tabAt.view;
      mRectF.top = view.getBottom() - mIndicatorHeight - mIndicatorMargin;
      mRectF.bottom = mRectF.top + mIndicatorHeight;
      calculationRectF(true);
    }
  }

  public void setViewPager(ViewPager2 viewPager,
      TabLayoutMediator.TabConfigurationStrategy tabConfigurationStrategy) {
    detach();
    mViewPager = viewPager;
    mViewPager.unregisterOnPageChangeCallback(mOnPageChangeCallback);
    mViewPager.registerOnPageChangeCallback(mOnPageChangeCallback);
    mTabLayoutMediator = new TabLayoutMediator(this, mViewPager, tabConfigurationStrategy);
    mTabLayoutMediator.attach();
  }

  private final ViewPager2.OnPageChangeCallback mOnPageChangeCallback =
      new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
          super.onPageScrolled(position, positionOffset, positionOffsetPixels);
          Tab tab = getTabAt(position);
          Tab nextTab = getTabAt(position + 1);
          if (tab != null && nextTab != null) {
            TabView view = tab.view;
            TabView nextView = nextTab.view;
            float width = view.getWidth() / 2f + nextView.getWidth() / 2f;
            float left = view.getLeft() + width * positionOffset;
            float right = view.getRight() + width * positionOffset;
            float centerX = getPaddingLeft() + left + (right - left) / 2f;
            mRectF.left = centerX - mIndicatorWidth / 2f;
            mRectF.right = centerX + mIndicatorWidth / 2f;
            postInvalidateOnAnimation();
          }
        }

        @Override
        public void onPageSelected(int position) {
          super.onPageSelected(position);
          Tab tabAt = getTabAt(position);
          if (tabAt != null) {
            tabAt.select();
          }
        }
      };

  private void calculationRectF(boolean skipAnimation) {
    int selectedTabPosition = getSelectedTabPosition();
    Tab tab = getTabAt(selectedTabPosition);
    if (tab != null) {
      View view = tab.view;
      if (view.getLeft() == 0 && view.getRight() == 0) {
        return;
      }
      int centerX = getPaddingLeft() + view.getLeft() + (view.getRight() - view.getLeft()) / 2;
      if (skipAnimation) {
        mRectF.left = centerX - mIndicatorWidth / 2f;
        mRectF.right = centerX + mIndicatorWidth / 2f;
        invalidate();
        return;
      }
      ValueAnimator valueAnimator = ValueAnimator.ofFloat(mRectF.centerX(), centerX);
      valueAnimator.setDuration(DURATION);
      valueAnimator.setInterpolator(mInterpolator);
      valueAnimator.start();
      valueAnimator.addUpdateListener(animation -> {
        float x = (float) animation.getAnimatedValue();
        mRectF.left = x - mIndicatorWidth / 2f;
        mRectF.right = x + mIndicatorWidth / 2f;
        invalidate();
      });
    }
  }

  @Override
  protected void dispatchDraw(@NonNull Canvas canvas) {
    if (mIndicatorBitmap != null) {
      canvas.drawBitmap(mIndicatorBitmap, mRectF.centerX() - mIndicatorBitmap.getWidth() / 2f,
          mRectF.bottom - mIndicatorBitmap.getHeight(), mPaint);
    } else {
      canvas.drawRoundRect(mRectF, mIndicatorCorner, mIndicatorCorner, mPaint);
    }
    super.dispatchDraw(canvas);
  }

  public void setSelectedIndex(int index) {
    for (int i = 0; i < getTabCount(); i++) {
      Tab tab = getTabAt(i);
      if (tab != null && index == i) {
        tab.select();
        return;
      }
    }
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    detach();
  }

  public void detach() {
    if (mViewPager != null) {
      mViewPager.unregisterOnPageChangeCallback(mOnPageChangeCallback);
    }
    if (mTabLayoutMediator != null) {
      mTabLayoutMediator.detach();
    }
  }
}
