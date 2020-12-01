package com.project.bookstore.model;

import javax.persistence.*;

@Entity
@Table(name = "ORDER_DETAIL", schema = "JRV77878", catalog = "")
@IdClass(OrderDetailEntityPK.class)
public class OrderDetailEntity {
  private int orderId;
  private int bid;
  private Integer quantity;
  private Double price;

  @Id
  @Column(name = "ORDER_ID")
  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }

  @Id
  @Column(name = "BID")
  public int getBid() {
    return bid;
  }

  public void setBid(int bid) {
    this.bid = bid;
  }

  @Basic
  @Column(name = "QUANTITY")
  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  @Basic
  @Column(name = "PRICE")
  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    OrderDetailEntity that = (OrderDetailEntity) o;

    if (orderId != that.orderId) return false;
    if (bid != that.bid) return false;
    if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
    if (price != null ? !price.equals(that.price) : that.price != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = orderId;
    result = 31 * result + bid;
    result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
    result = 31 * result + (price != null ? price.hashCode() : 0);
    return result;
  }
}
