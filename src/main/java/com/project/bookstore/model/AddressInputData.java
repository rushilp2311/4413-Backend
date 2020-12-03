package com.project.bookstore.model;

public class AddressInputData extends InputData{
  private int streetNo;
  private String streetName;
  private String city;
  private String province;
  private String country;
  private String zip;
  private String phone;

  public AddressInputData() {
  }

  public int getStreetNo() {
    return streetNo;
  }

  public void setStreetNo(int streetNo) {
    this.streetNo = streetNo;
  }

  public String getStreetName() {
    return streetName;
  }

  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
}
