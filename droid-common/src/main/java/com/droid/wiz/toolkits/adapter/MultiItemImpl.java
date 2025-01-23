package com.droid.wiz.toolkits.adapter;

/**
 * Created by fangzheng on 2021/11/11.
 */
public class MultiItemImpl implements MultiItem {

  private final int itemType;

  public MultiItemImpl(int itemType) {
    this.itemType = itemType;
  }

  @Override
  public int getItemType() {
    return itemType;
  }
}
