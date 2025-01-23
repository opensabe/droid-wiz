package com.droid.wiz.toolkits.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

/**
 * Created by liuXiao on 07/13/2021
 */
public abstract class BaseMultiItemAdapter<T extends com.droid.wiz.toolkits.adapter.MultiItem> extends
    BaseAdapter<T, ViewBinding> {

  @Override
  public int getItemViewType(int position) {
    MultiItem item = getItem(position);
    if (item != null) {
      return item.getItemType();
    }
    return super.getItemViewType(position);
  }

  @NonNull
  @Override
  public BaseViewHolder<ViewBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return super.onCreateViewHolder(parent, viewType);
  }
}
