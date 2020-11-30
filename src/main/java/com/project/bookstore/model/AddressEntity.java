package com.project.bookstore.model;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESS", schema = "JRV77878", catalog = "")
public class AddressEntity {
  private int addressId;
  private Integer streetNo;
  private String streetName;
  private String city;
  private String province;
  private String country;
  private String zip;
  private String phone;

  @Id
  @Column(name = "ADDRESS_ID")
  public int getAddressId() {
    return addressId;
  }

  public void setAddressId(int addressId) {
    this.addressId = addressId;
  }

  @Basic
  @Column(name = "STREET_NO")
  public Integer getStreetNo() {
    return streetNo;
  }

  public void setStreetNo(Integer streetNo) {
    this.streetNo = streetNo;
  }

  @Basic
  @Column(name = "STREET_NAME")
  public String getStreetName() {
    return streetName;
  }

  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }

  @Basic
  @Column(name = "CITY")
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  @Basic
  @Column(name = "PROVINCE")
  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  @Basic
  @Column(name = "COUNTRY")
  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  @Basic
  @Column(name = "ZIP")
  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  @Basic
  @Column(name = "PHONE")
  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    AddressEntity that = (AddressEntity) o;

    if (addressId != that.addressId) return false;
    if (streetNo != null ? !streetNo.equals(that.streetNo) : that.streetNo != null) return false;
    if (streetName != null ? !streetName.equals(that.streetName) : that.streetName != null) return false;
    if (city != null ? !city.equals(that.city) : that.city != null) return false;
    if (province != null ? !province.equals(that.province) : that.province != null) return false;
    if (country != null ? !country.equals(that.country) : that.country != null) return false;
    if (zip != null ? !zip.equals(that.zip) : that.zip != null) return false;
    if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = addressId;
    result = 31 * result + (streetNo != null ? streetNo.hashCode() : 0);
    result = 31 * result + (streetName != null ? streetName.hashCode() : 0);
    result = 31 * result + (city != null ? city.hashCode() : 0);
    result = 31 * result + (province != null ? province.hashCode() : 0);
    result = 31 * result + (country != null ? country.hashCode() : 0);
    result = 31 * result + (zip != null ? zip.hashCode() : 0);
    result = 31 * result + (phone != null ? phone.hashCode() : 0);
    return result;
  }
}
