package com.droid.wiz.toolkits.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

@SuppressWarnings("unused")
public abstract class BaseAdapter<T, V extends ViewBinding> extends
    RecyclerView.Adapter<BaseViewHolder<V>> {

  public final List<T> mList = new ArrayList<>();
  private RecyclerView mRecyclerView;
  private LayoutInflater mLayoutInflater;
  private OnItemClickListener mOnItemClickListener;
  private OnItemLongClickListener mOnItemLongClickListener;
  protected OnItemChildClickListener mOnItemChildClickListener;
  protected OnItemChildLongClickListener mOnItemChildLongClickListener;
  private final Set<Integer> mChildClickViewIds = new LinkedHashSet<>();

  @NonNull
  @Override
  public BaseViewHolder<V> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    BaseViewHolder<V> baseViewHolder = new BaseViewHolder<>(createViewBinding(parent, viewType));
    bindViewClickListener(baseViewHolder);
    return baseViewHolder;
  }

  private void bindViewClickListener(BaseViewHolder<V> baseViewHolder) {
    if (mOnItemClickListener != null) {
      baseViewHolder.itemView.setOnClickListener(v -> {
        int adapterPosition = baseViewHolder.getAdapterPosition();
        if (adapterPosition == RecyclerView.NO_POSITION) {
          return;
        }
        mOnItemClickListener.onItemClick(baseViewHolder.itemView, adapterPosition);
      });
    }
    if (mOnItemLongClickListener != null) {
      baseViewHolder.itemView.setOnLongClickListener(v -> {
        int adapterPosition = baseViewHolder.getAdapterPosition();
        if (adapterPosition == RecyclerView.NO_POSITION) {
          return false;
        }
        return mOnItemLongClickListener.onItemLongClick(v, adapterPosition);
      });
    }
    if (mOnItemChildClickListener != null) {
      for (Integer childClickViewId : mChildClickViewIds) {
        View view = baseViewHolder.itemView.findViewById(childClickViewId);
        if (view != null) {
          view.setOnClickListener(v -> {
            int adapterPosition = baseViewHolder.getAdapterPosition();
            if (adapterPosition == RecyclerView.NO_POSITION) {
              return;
            }
            mOnItemChildClickListener.onItemChildClick(v, adapterPosition);
          });
        }
      }
    }
    if (mOnItemChildLongClickListener != null) {
      for (Integer childClickViewId : mChildClickViewIds) {
        View view = baseViewHolder.itemView.findViewById(childClickViewId);
        if (view != null) {
          view.setOnLongClickListener(v -> {
            int adapterPosition = baseViewHolder.getAdapterPosition();
            if (adapterPosition == RecyclerView.NO_POSITION) {
              return false;
            }
            return mOnItemChildLongClickListener.onItemChildLongClick(v, adapterPosition);
          });
        }
      }
    }
  }

  @Override
  public void onBindViewHolder(@NonNull BaseViewHolder<V> holder, int position) {
    T item = getItem(position);
    if (item != null) {
      convert(holder.binding, mList.get(position), position);
    }
  }

  @Override
  public void onBindViewHolder(@NonNull BaseViewHolder<V> holder, int position,
      @NonNull List<Object> payloads) {
    T item = getItem(position);
    if (item != null) {
      convert(holder.binding, mList.get(position), position, payloads);
    }
  }

  @NonNull
  public abstract V createViewBinding(@NonNull ViewGroup parent, int viewType);

  protected abstract void convert(@NonNull V binding, @NonNull T item, int position);

  protected void convert(@NonNull V binding, @NonNull T item, int position,
      @NonNull List<Object> payloads) {
    convert(binding, item, position);
  }

  @Override
  public int getItemCount() {
    return mList.size();
  }

  public void setList(@Nullable List<? extends T> list) {
    mList.clear();
    if (list != null && list.size() > 0) {
      mList.addAll(list);
    }
    updateAll();
  }

  public void clearList() {
    mList.clear();
    updateAll();
  }

  public boolean isEmpty() {
    return mList.isEmpty();
  }

  @NonNull
  public List<T> getList() {
    return mList;
  }

  public int getListSize() {
    return mList.size();
  }

  public void addList(int index, @Nullable List<? extends T> list) {
    if (list != null && list.size() > 0 && index >= 0 && index <= mList.size()) {
      mList.addAll(index, list);
      notifyItemRangeInserted(index, list.size());
    }
  }

  public void addList(@Nullable List<? extends T> list) {
    addList(mList.size(), list);
  }

  public void removeList(int index, int count) {
    if (index >= 0 && index + count <= mList.size()) {
      mList.subList(index, index + count).clear();
      notifyItemRangeRemoved(index, count);
    }
  }

  public void removeList(@Nullable List<? extends T> data) {
    if (data != null) {
      mList.removeAll(data);
      updateAll();
    }
  }

  @Nullable
  public T getItem(int index) {
    if (index >= 0 && index < mList.size()) {
      return mList.get(index);
    }
    return null;
  }

  public int getIndex(@Nullable T item) {
    return mList.indexOf(item);
  }

  public void addItem(@Nullable T item) {
    addItem(mList.size(), item);
  }

  public void addItem(int index, @Nullable T item) {
    if (item != null && index >= 0 && index <= mList.size()) {
      mList.add(index, item);
      notifyItemInserted(index);
    }
  }

  public void removeItem(int index) {
    if (index >= 0 && index < mList.size()) {
      mList.remove(index);
      notifyItemRemoved(index);
    }
  }

  public void removeItem(@Nullable T item) {
    removeItem(mList.indexOf(item));
  }

  public void updateItem(int index, @Nullable Object payload) {
    if (index >= 0 && index < mList.size()) {
      notifyItemChanged(index, payload);
    }
  }

  public void updateItem(@Nullable T item, @Nullable Object payload) {
    updateItem(mList.indexOf(item), payload);
  }

  public void updateItem(int index) {
    updateItem(index, null);
  }

  public void updateItem(@Nullable T item) {
    updateItem(item, null);
  }

  public void setItem(int index, @Nullable T item) {
    if (index >= 0 && index < mList.size()) {
      mList.set(index, item);
      updateItem(index);
    }
  }

  @SuppressLint("NotifyDataSetChanged")
  public void updateAll() {
    notifyDataSetChanged();
  }

  public LayoutInflater getLayoutInflater() {
    if (mLayoutInflater == null) {
      mLayoutInflater = LayoutInflater.from(getContext());
    }
    return mLayoutInflater;
  }

  public RecyclerView getRecyclerView() {
    return mRecyclerView;
  }

  public Context getContext() {
    return mRecyclerView.getContext();
  }

  @Override
  public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
    super.onAttachedToRecyclerView(recyclerView);
    this.mRecyclerView = recyclerView;
  }

  public void setOnItemClickListener(OnItemClickListener listener) {
    mOnItemClickListener = listener;
  }

  public void setOnItemLongClickListener(OnItemLongClickListener listener) {
    mOnItemLongClickListener = listener;
  }

  public void setOnItemChildClickListener(OnItemChildClickListener listener) {
    mOnItemChildClickListener = listener;
  }

  public void setOnItemChildLongClickListener(OnItemChildLongClickListener listener) {
    mOnItemChildLongClickListener = listener;
  }

  public void addChildClickViewIds(Integer... id) {
    mChildClickViewIds.addAll(Arrays.asList(id));
  }
}
