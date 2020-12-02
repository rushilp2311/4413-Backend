package com.project.bookstore.model;

import java.util.List;

public class CartInputData {
  private String userId;
  private List<OrderDetailEntity> itemList;

  public CartInputData() { }

  public CartInputData(String userId, List<OrderDetailEntity> itemList) {
    this.userId = userId;
    this.itemList = itemList;
  }

  public List<OrderDetailEntity> getItemList() {
    return itemList;
  }

  public void setItemList(List<OrderDetailEntity> itemList) {
    this.itemList = itemList;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
}
