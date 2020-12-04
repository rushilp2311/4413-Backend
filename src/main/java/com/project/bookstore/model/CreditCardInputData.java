package com.project.bookstore.model;

import org.springframework.util.StringUtils;

public class CreditCardInputData extends InputData {
  private String name;
  private String number; // CC number as 16 digit (no hyphens)
  private String expiry; // CC expiry as 5 letter 'mm/yy' string
  private String cvv; // CC security code as 3 digit string

  public CreditCardInputData() {
  }

  public boolean isNameValid(){
    return (!name.equals("") && !StringUtils.isEmpty(name) && name.length() > 0 && name.length() < 30);
  }

  public boolean isNumberValid(){
    return this.number.matches("(?:[1-9][0-9]{15})|");
  }
  public boolean isExpiryValid(){
    return this.expiry.matches("(?:0[1-9]|1[0-2])/[0-9]{2}");
  }
  public boolean isCvvValid(){
    return this.cvv.matches("^[0-9]{3}$");
  }
  public boolean isValid(){
    return isNameValid() && isNumberValid() && isExpiryValid() && isCvvValid();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getExpiry() {
    return expiry;
  }

  public void setExpiry(String expiry) {
    this.expiry = expiry;
  }

  public String getCvv() {
    return cvv;
  }

  public void setCvv(String cvv) {
    this.cvv = cvv;
  }

}
