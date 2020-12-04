package com.project.bookstore.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "ORDER", schema = "JRV77878", catalog = "")
public class OrderEntity {
  private int orderId;
  private String userId;
  private Date orderDate;
  private Integer status;
  private Integer submit_attempts;

  @OneToMany(mappedBy = "order")
  private List<OrderDetailEntity> orderDetails;

//  @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderDetailEntity", targetEntity = OrderDetailEntity.class, fetch = FetchType.EAGER)
//  private List<OrderDetailEntity> itemList;
//
//  public List<OrderDetailEntity> getItemList() {
//    return itemList;
//  }
//
//  public void setItemList(List<OrderDetailEntity> itemList) {
//    this.itemList = itemList;
//  }

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

  @Basic
  @Column(name = "SUBMIT_ATTEMPTS")
  public Integer getSubmit_attempts() {
    return submit_attempts;
  }

  public void setSubmit_attempts(Integer submit_attempts) {
    this.submit_attempts = submit_attempts;
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
