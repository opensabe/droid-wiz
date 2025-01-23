package com.droid.wiz.toolkits.bus;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

/**
 * Created by liuXiao on 09/27/2021
 */
public abstract class MReceiver {

  public final Set<String> codes = new HashSet<>();

  public MReceiver(String... codes) {
    this.codes.addAll(Arrays.asList(codes));
  }

  public void addCode(String... codes) {
    this.codes.addAll(Arrays.asList(codes));
  }

  protected abstract void onReceiver(@NonNull String code, @Nullable Object data);

  public MReceiver bindLifecycle(LifecycleOwner lifecycleOwner) {
    if (lifecycleOwner != null) {
      Lifecycle lifecycle = lifecycleOwner.getLifecycle();
      lifecycle.addObserver(new DefaultLifecycleObserver() {
        @Override
        public void onDestroy(@NonNull LifecycleOwner owner) {
          MBus.unregister(MReceiver.this);
        }
      });
    }
    return this;
  }
}
