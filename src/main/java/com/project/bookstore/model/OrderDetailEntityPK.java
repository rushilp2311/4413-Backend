package com.project.bookstore.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class OrderDetailEntityPK implements Serializable {
  private int orderId;
  private int bid;

  @Column(name = "ORDER_ID")
  @Id
  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }

  @Column(name = "BID")
  @Id
  public int getBid() {
    return bid;
  }

  public void setBid(int bid) {
    this.bid = bid;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    OrderDetailEntityPK that = (OrderDetailEntityPK) o;

    if (orderId != that.orderId) return false;
    if (bid != that.bid) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = orderId;
    result = 31 * result + bid;
    return result;
  }
}
