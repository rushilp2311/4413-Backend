package com.project.bookstore.model;

import org.springframework.util.StringUtils;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity (name = "user")
public class UserEntity {

  @Id
  @Column
  private String user_id;
  @Basic
  @Column
  private String first_name;
  @Basic
  @Column
  private String last_name;
  @Basic
  @Column
  private String email;
  @Basic
  @Column
  private String password;
  @Basic
  @Column
  private Integer user_type;

  public UserEntity(String user_id, String first_name, String last_name, String email, String password, Integer user_type) {
    this.user_id = user_id;
    this.first_name = first_name;
    this.last_name = last_name;
    this.email = email;
    this.password = password;
    this.user_type = user_type;
  }

  public UserEntity() {
  }

  public String getUser_id() {
    return user_id;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  public String getFirst_name() {
    return first_name;
  }

  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  public String getLast_name() {
    return last_name;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Integer getUser_type() {
    return user_type;
  }

  public void setUser_type(Integer user_type) {
    this.user_type = user_type;
  }

  @Override
  public String toString() {
    return "UserEntity{" +
            "user_id='" + user_id + '\'' +
            ", first_name='" + first_name + '\'' +
            ", last_name='" + last_name + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", user_type=" + user_type +
            '}';
  }

  public boolean isValid(){
    return !StringUtils.isEmpty(email) && !StringUtils.isEmpty(first_name) && !StringUtils.isEmpty(last_name) && !StringUtils.isEmpty(password)
            && email.length() > 0 && email.length() <= 40 && password.length() > 6 && password.length() <= 80 && first_name.length() > 0 && first_name.length() < 30
            && last_name.length() > 0 && last_name.length() < 30;
  }
}
