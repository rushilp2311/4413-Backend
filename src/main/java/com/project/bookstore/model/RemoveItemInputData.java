package com.project.bookstore.model;

public class RemoveItemInputData {
  private int bid;
  private String userId;

  public RemoveItemInputData() {
  }

  public int getBid() {
    return bid;
  }

  public void setBid(int bid) {
    this.bid = bid;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
}
