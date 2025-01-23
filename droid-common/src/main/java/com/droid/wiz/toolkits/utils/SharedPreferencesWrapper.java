package com.droid.wiz.toolkits.utils;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.getkeepsafe.relinker.ReLinker;
import com.tencent.mmkv.MMKV;

import androidx.annotation.Nullable;

public class SharedPreferencesWrapper implements SharedPreferences, SharedPreferences.Editor {

  private static final String IMPORT_FROM_SHARED_PREFERENCES_SUCCESS =
      "importFromSharedPreferencesSuccess";
  private volatile static boolean successfully;
  private static Application context;

  public static void init(Application application) {
    context = application;
    try {
      if (!isSupportMMKV()) {
        successfully = false;
        return;
      }
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        MMKV.initialize(context);
        successfully = true;
        return;
      }
      MMKV.initialize(context, libName -> ReLinker.loadLibrary(application, libName));
      successfully = true;
    } catch (Throwable ignored) {
      successfully = false;
    }
  }

  private SharedPreferences sharedPreferences;

  public static SharedPreferences getSharedPreferences(String name) {
    return new SharedPreferencesWrapper(name, Context.MODE_PRIVATE, true);
  }

  public static SharedPreferences getSharedPreferences(String name, int mode, boolean applyMMKV) {
    return new SharedPreferencesWrapper(name, mode, applyMMKV);
  }

  public SharedPreferencesWrapper(String name, int mode, boolean applyMMKV) {
    if (successfully && applyMMKV) {
      MMKV mmkv = TextUtils.isEmpty(name) ? MMKV.defaultMMKV() : MMKV.mmkvWithID(name);
      if (!mmkv.getBoolean(IMPORT_FROM_SHARED_PREFERENCES_SUCCESS, false)) {
        mmkv.encode(IMPORT_FROM_SHARED_PREFERENCES_SUCCESS, true);
        SharedPreferences oldSharedPreferences = getOldSharedPreferences(name, mode);
        mmkv.importFromSharedPreferences(oldSharedPreferences);
      }
      sharedPreferences = mmkv;
    }
    if (sharedPreferences == null) {
      sharedPreferences = getOldSharedPreferences(name, mode);
    }
  }

  private SharedPreferences getOldSharedPreferences(String name, int mode) {
    if (TextUtils.isEmpty(name)) {
      return PreferenceManager.getDefaultSharedPreferences(context);
    }
    return context.getSharedPreferences(name, mode);
  }

  /**
   * Only supported MMKV SharedPreferences
   */
  @Override
  public Map<String, ?> getAll() {
    return sharedPreferences.getAll();
  }

  /**
   * Only supported MMKV
   */
  @Nullable
  public String[] allKeys() {
    if (isMMKV()) {
      return ((MMKV) sharedPreferences).allKeys();
    }
    return null;
  }

  @Nullable
  @Override
  public String getString(String key, @Nullable String defValue) {
    return sharedPreferences.getString(key, defValue);
  }

  @Nullable
  @Override
  public Set<String> getStringSet(String key, @Nullable Set<String> defValues) {
    return sharedPreferences.getStringSet(key, defValues);
  }

  @Override
  public int getInt(String key, int defValue) {
    return sharedPreferences.getInt(key, defValue);
  }

  @Override
  public long getLong(String key, long defValue) {
    return sharedPreferences.getLong(key, defValue);
  }

  @Override
  public float getFloat(String key, float defValue) {
    return sharedPreferences.getFloat(key, defValue);
  }

  @Override
  public boolean getBoolean(String key, boolean defValue) {
    return sharedPreferences.getBoolean(key, defValue);
  }

  @Override
  public boolean contains(String key) {
    return sharedPreferences.contains(key);
  }

  @Override
  public Editor edit() {
    return sharedPreferences.edit();
  }

  @Override
  public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
    sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
  }

  @Override
  public void unregisterOnSharedPreferenceChangeListener(
      OnSharedPreferenceChangeListener listener) {
    sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
  }

  @Override
  public Editor putString(String key, @Nullable String value) {
    return edit().putString(key, value);
  }

  @Override
  public Editor putStringSet(String key, @Nullable Set<String> values) {
    return edit().putStringSet(key, values);
  }

  @Override
  public Editor putInt(String key, int value) {
    return edit().putInt(key, value);
  }

  @Override
  public Editor putLong(String key, long value) {
    return edit().putLong(key, value);
  }

  @Override
  public Editor putFloat(String key, float value) {
    return edit().putFloat(key, value);
  }

  @Override
  public Editor putBoolean(String key, boolean value) {
    return edit().putBoolean(key, value);
  }

  @Override
  public Editor remove(String key) {
    return edit().remove(key);
  }

  @Override
  public Editor clear() {
    return edit().clear();
  }

  @Override
  public boolean commit() {
    if (isMMKV()) {
      return false;
    }
    return edit().commit();
  }

  @Override
  public void apply() {
    if (isMMKV()) {
      return;
    }
    edit().apply();
  }

  private boolean isMMKV() {
    return sharedPreferences instanceof MMKV;
  }

  public static boolean isSupportMMKV() {
    String cpuArchitecture = SystemUtils.getCpuArchitecture();
    return Objects.equals("arm64-v8a", cpuArchitecture) ||
        Objects.equals("armeabi-v7a", cpuArchitecture) ||
        Objects.equals("x86_64", cpuArchitecture) || Objects.equals("x86", cpuArchitecture);
  }
}
