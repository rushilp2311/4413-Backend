package com.project.bookstore.model;

import java.util.List;

public class CartInputData {
  private String userId;
  private List<CartItemInputData> itemList;

  public CartInputData() { }

  public CartInputData(String userId, List<CartItemInputData> itemList) {
    this.userId = userId;
    this.itemList = itemList;
  }

  public List<CartItemInputData> getItemList() {
    return itemList;
  }

  public void setItemList(List<CartItemInputData> itemList) {
    this.itemList = itemList;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
}
