package com.droid.wiz.toolkits.utils;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.collection.SimpleArrayMap;

@SuppressLint("ApplySharedPref")
public final class SPUtils {

  private static final SimpleArrayMap<String, SPUtils> SP_UTILS_MAP = new SimpleArrayMap<>();
  private final SharedPreferences mSharedPreferences;

  /**
   * 获取 SP 实例
   *
   * @return {@link SPUtils}
   */
  public static SPUtils getInstance() {
    return getInstance("");
  }

  /**
   * 获取 SP 实例
   *
   * @param spName sp 名
   * @return {@link SPUtils}
   */
  public static SPUtils getInstance(String spName) {
    if (isSpace(spName)) {
      spName = Tools.getContext().getPackageName().concat(".data");
    }
    SPUtils spUtils = SP_UTILS_MAP.get(spName);
    if (spUtils == null) {
      spUtils = new SPUtils(spName);
      SP_UTILS_MAP.put(spName, spUtils);
    }
    return spUtils;
  }

  private SPUtils(final String spName) {
    mSharedPreferences = SharedPreferencesWrapper.getSharedPreferences(spName);
  }

  /**
   * SP 中写入 String
   *
   * @param key   键
   * @param value 值
   */
  public void putApply(@NonNull final String key, @NonNull final String value) {
    putApply(key, value, false);
  }

  /**
   * SP 中写入 String
   *
   * @param key      键
   * @param value    值
   * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
   *                 {@code false}: {@link SharedPreferences.Editor#apply()}
   */
  public void putApply(@NonNull final String key, @NonNull final String value, final boolean isCommit) {
    if (isCommit) {
      mSharedPreferences.edit().putString(key, value).commit();
    } else {
      mSharedPreferences.edit().putString(key, value).apply();
    }
  }

  /**
   * SP 中读取 String
   *
   * @param key 键
   * @return 存在返回对应值，不存在返回默认值{@code ""}
   */
  public String getString(@NonNull final String key) {
    return getString(key, "");
  }

  /**
   * SP 中读取 String
   *
   * @param key          键
   * @param defaultValue 默认值
   * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
   */
  public String getString(@NonNull final String key, @NonNull final String defaultValue) {
    return mSharedPreferences.getString(key, defaultValue);
  }

  /**
   * SP 中写入 int
   *
   * @param key   键
   * @param value 值
   */
  public void putApply(@NonNull final String key, final int value) {
    putApply(key, value, false);
  }

  /**
   * SP 中写入 int
   *
   * @param key      键
   * @param value    值
   * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
   *                 {@code false}: {@link SharedPreferences.Editor#apply()}
   */
  public void putApply(@NonNull final String key, final int value, final boolean isCommit) {
    if (isCommit) {
      mSharedPreferences.edit().putInt(key, value).commit();
    } else {
      mSharedPreferences.edit().putInt(key, value).apply();
    }
  }

  /**
   * SP 中读取 int
   *
   * @param key 键
   * @return 存在返回对应值，不存在返回默认值-1
   */
  public int getInt(@NonNull final String key) {
    return getInt(key, -1);
  }

  /**
   * SP 中读取 int
   *
   * @param key          键
   * @param defaultValue 默认值
   * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
   */
  public int getInt(@NonNull final String key, final int defaultValue) {
    return mSharedPreferences.getInt(key, defaultValue);
  }

  /**
   * SP 中写入 long
   *
   * @param key   键
   * @param value 值
   */
  public void putApply(@NonNull final String key, final long value) {
    putApply(key, value, false);
  }

  public void putLong(@NonNull final String key, final long value) {
    putLong(key, value, false);
  }

  /**
   * SP 中写入 long
   *
   * @param key      键
   * @param value    值
   * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
   *                 {@code false}: {@link SharedPreferences.Editor#apply()}
   */
  public void putApply(@NonNull final String key, final long value, final boolean isCommit) {
    if (isCommit) {
      mSharedPreferences.edit().putLong(key, value).commit();
    } else {
      mSharedPreferences.edit().putLong(key, value).apply();
    }
  }

  public void putLong(@NonNull final String key, final long value, final boolean isCommit) {
    if (isCommit) {
      mSharedPreferences.edit().putLong(key, value).commit();
    } else {
      mSharedPreferences.edit().putLong(key, value).apply();
    }
  }

  /**
   * SP 中读取 long
   *
   * @param key 键
   * @return 存在返回对应值，不存在返回默认值-1
   */
  public long getLong(@NonNull final String key) {
    return getLong(key, -1L);
  }

  /**
   * SP 中读取 long
   *
   * @param key          键
   * @param defaultValue 默认值
   * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
   */
  public long getLong(@NonNull final String key, final long defaultValue) {
    return mSharedPreferences.getLong(key, defaultValue);
  }

  /**
   * SP 中写入 float
   *
   * @param key   键
   * @param value 值
   */
  public void putApply(@NonNull final String key, final float value) {
    putApply(key, value, false);
  }

  /**
   * SP 中写入 float
   *
   * @param key      键
   * @param value    值
   * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
   *                 {@code false}: {@link SharedPreferences.Editor#apply()}
   */
  public void putApply(@NonNull final String key, final float value, final boolean isCommit) {
    if (isCommit) {
      mSharedPreferences.edit().putFloat(key, value).commit();
    } else {
      mSharedPreferences.edit().putFloat(key, value).apply();
    }
  }

  /**
   * SP 中读取 float
   *
   * @param key 键
   * @return 存在返回对应值，不存在返回默认值-1
   */
  public float getFloat(@NonNull final String key) {
    return getFloat(key, -1f);
  }

  /**
   * SP 中读取 float
   *
   * @param key          键
   * @param defaultValue 默认值
   * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
   */
  public float getFloat(@NonNull final String key, final float defaultValue) {
    return mSharedPreferences.getFloat(key, defaultValue);
  }

  /**
   * SP 中写入 boolean
   *
   * @param key   键
   * @param value 值
   */
  public void putApply(@NonNull final String key, final boolean value) {
    putApply(key, value, false);
  }

  /**
   * SP 中写入 boolean
   *
   * @param key      键
   * @param value    值
   * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
   *                 {@code false}: {@link SharedPreferences.Editor#apply()}
   */
  public void putApply(@NonNull final String key, final boolean value, final boolean isCommit) {
    if (isCommit) {
      mSharedPreferences.edit().putBoolean(key, value).commit();
    } else {
      mSharedPreferences.edit().putBoolean(key, value).apply();
    }
  }

  /**
   * SP 中读取 boolean
   *
   * @param key 键
   * @return 存在返回对应值，不存在返回默认值{@code false}
   */
  public boolean getBoolean(@NonNull final String key) {
    return getBoolean(key, false);
  }

  /**
   * SP 中读取 boolean
   *
   * @param key          键
   * @param defaultValue 默认值
   * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
   */
  public boolean getBoolean(@NonNull final String key, final boolean defaultValue) {
    return mSharedPreferences.getBoolean(key, defaultValue);
  }

  /**
   * SP 中写入 String 集合
   *
   * @param key    键
   * @param values 值
   */
  public void putApply(@NonNull final String key, @NonNull final Set<String> values) {
    putApply(key, values, false);
  }

  /**
   * SP 中写入 String 集合
   *
   * @param key      键
   * @param values   值
   * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
   *                 {@code false}: {@link SharedPreferences.Editor#apply()}
   */
  public void putApply(@NonNull final String key, @NonNull final Set<String> values,
      final boolean isCommit) {
    if (isCommit) {
      mSharedPreferences.edit().putStringSet(key, values).commit();
    } else {
      mSharedPreferences.edit().putStringSet(key, values).apply();
    }
  }

  /**
   * SP 中读取 StringSet
   *
   * @param key 键
   * @return 存在返回对应值，不存在返回默认值{@code Collections.<String>emptySet()}
   */
  public Set<String> getStringSet(@NonNull final String key) {
    return getStringSet(key, Collections.<String>emptySet());
  }

  /**
   * SP 中读取 StringSet
   *
   * @param key          键
   * @param defaultValue 默认值
   * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
   */
  public Set<String> getStringSet(@NonNull final String key,
      @NonNull final Set<String> defaultValue) {
    return mSharedPreferences.getStringSet(key, defaultValue);
  }

  /**
   * SP 中获取所有键值对
   *
   * @return Map 对象
   */
  public Map<String, ?> getAll() {
    return mSharedPreferences.getAll();
  }

  /**
   * SP 中是否存在该 key
   *
   * @param key 键
   * @return {@code true}: 存在<br>{@code false}: 不存在
   */
  public boolean contains(@NonNull final String key) {
    return mSharedPreferences.contains(key);
  }

  /**
   * SP 中移除该 key
   *
   * @param key 键
   */
  public void remove(@NonNull final String key) {
    remove(key, false);
  }

  /**
   * SP 中移除该 key
   *
   * @param key      键
   * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
   *                 {@code false}: {@link SharedPreferences.Editor#apply()}
   */
  public void remove(@NonNull final String key, final boolean isCommit) {
    if (isCommit) {
      mSharedPreferences.edit().remove(key).commit();
    } else {
      mSharedPreferences.edit().remove(key).apply();
    }
  }

  /**
   * SP 中清除所有数据
   */
  public void clear() {
    clear(false);
  }

  /**
   * SP 中清除所有数据
   *
   * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
   *                 {@code false}: {@link SharedPreferences.Editor#apply()}
   */
  public void clear(final boolean isCommit) {
    if (isCommit) {
      mSharedPreferences.edit().clear().commit();
    } else {
      mSharedPreferences.edit().clear().apply();
    }
  }

  private static boolean isSpace(final String s) {
    if (s == null) {
      return true;
    }
    for (int i = 0, len = s.length(); i < len; ++i) {
      if (!Character.isWhitespace(s.charAt(i))) {
        return false;
      }
    }
    return true;
  }
}
