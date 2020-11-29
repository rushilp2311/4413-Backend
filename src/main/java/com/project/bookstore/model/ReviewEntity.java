package com.project.bookstore.model;

import javax.persistence.*;

@Entity
@Table(name = "REVIEW", schema = "JRV77878", catalog = "")
public class ReviewEntity {
  private int reviewId;
  private int bid;
  private String userId;
  private Double stars;
  private String message;

  @Id
  @Column(name = "REVIEW_ID")
  public int getReviewId() {
    return reviewId;
  }

  public void setReviewId(int reviewId) {
    this.reviewId = reviewId;
  }

  @Basic
  @Column(name = "BID")
  public int getBid() {
    return bid;
  }

  public void setBid(int bid) {
    this.bid = bid;
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
  @Column(name = "STARS")
  public Double getStars() {
    return stars;
  }

  public void setStars(Double stars) {
    this.stars = stars;
  }

  @Basic
  @Column(name = "MESSAGE")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ReviewEntity that = (ReviewEntity) o;

    if (reviewId != that.reviewId) return false;
    if (bid != that.bid) return false;
    if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
    if (stars != null ? !stars.equals(that.stars) : that.stars != null) return false;
    if (message != null ? !message.equals(that.message) : that.message != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = reviewId;
    result = 31 * result + bid;
    result = 31 * result + (userId != null ? userId.hashCode() : 0);
    result = 31 * result + (stars != null ? stars.hashCode() : 0);
    result = 31 * result + (message != null ? message.hashCode() : 0);
    return result;
  }
}
