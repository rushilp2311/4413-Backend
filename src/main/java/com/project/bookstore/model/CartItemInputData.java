package com.project.bookstore.model;

public class CartItemInputData {

  private String userId;
  private int bid;
  private int quantity;
  private double price;

  public CartItemInputData() { }

  public CartItemInputData(String userId, int bid, int quantity, double price) {
    this.userId = userId;
    this.bid = bid;
    this.quantity = quantity;
    this.price = price;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public int getBid() {
    return bid;
  }

  public void setBid(int bid) {
    this.bid = bid;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }
}
