package com.project.bookstore.model;

public class OrderProcessedData {
  private String title;
  private double price;
  private int orderId;
  private String userId;
  private String orderDate;

  public OrderProcessedData() {
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(String orderDate) {
    this.orderDate = orderDate;
  }

  public OrderProcessedData(String title, double price, int orderId, String userId, String orderDate) {
    this.title = title;
    this.price = price;
    this.orderId = orderId;
    this.userId = userId;
    this.orderDate = orderDate;
  }
}
