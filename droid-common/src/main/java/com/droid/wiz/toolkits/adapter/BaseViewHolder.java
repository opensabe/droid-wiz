package com.droid.wiz.toolkits.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

/**
 * Created by liuXiao on 07/13/2021
 */
public class BaseViewHolder<V extends ViewBinding> extends RecyclerView.ViewHolder {
  public @NonNull
  V binding;

  public BaseViewHolder(@NonNull V v) {
    super(v.getRoot());
    binding = v;
  }
}
