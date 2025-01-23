package com.droid.wiz.toolkits.lifecycle;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

public class DestroyedLifecycleOwner implements LifecycleOwner {

  private final LifecycleRegistry mLifecycleRegistry;

  public DestroyedLifecycleOwner() {
    mLifecycleRegistry = new LifecycleRegistry(this);
    mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
  }

  @NonNull
  @Override
  public Lifecycle getLifecycle() {
    return mLifecycleRegistry;
  }
}