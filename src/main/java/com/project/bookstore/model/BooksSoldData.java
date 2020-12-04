package com.project.bookstore.model;

import java.math.BigInteger;

public class BooksSoldData {
  private int bid;
  private String title;
  private double price;
  private int quantity;

  public BooksSoldData() {
  }

  public BooksSoldData(int bid, String title, double price, int quantity) {
    this.bid = bid;
    this.title = title;
    this.price = price;
    this.quantity = quantity;
  }

  public int getBid() {
    return bid;
  }

  public void setBid(int bid) {
    this.bid = bid;
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

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
