package com.project.bookstore.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "ORDER", schema = "JRV77878", catalog = "")
public class OrderEntity {
  private int orderId;
  private String userId;
  private Date orderDate;
  private Integer status;

  @Id
  @Column(name = "ORDER_ID")
  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }

  @Basic
  @Column(name = "USER_ID")
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  @Basic
  @Column(name = "ORDER_DATE")
  public Date getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(Date orderDate) {
    this.orderDate = orderDate;
  }

  @Basic
  @Column(name = "STATUS")
  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    OrderEntity that = (OrderEntity) o;

    if (orderId != that.orderId) return false;
    if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
    if (orderDate != null ? !orderDate.equals(that.orderDate) : that.orderDate != null) return false;
    if (status != null ? !status.equals(that.status) : that.status != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = orderId;
    result = 31 * result + (userId != null ? userId.hashCode() : 0);
    result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
    result = 31 * result + (status != null ? status.hashCode() : 0);
    return result;
  }
}
