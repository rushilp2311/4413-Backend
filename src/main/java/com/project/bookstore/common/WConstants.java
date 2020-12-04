package com.project.bookstore.common;

/* This file contains any important constants or repetitive error codes that will be used in app */

public class WConstants {

  // Please use these response codes when communicating with front-end
  public static final int RESPONSE_SUCCESS = 0;
  public static final int RESPONSE_FAIL = 1;

  public static final int RESULT_UNKNOWN_ERROR = -10;

  // Error Codes
  public static final int RESULT_INVALID_DATA = -100;
  public static final int INVALID_USER_SIGNUP_DATA = -1;
  public static final int RESULT_USER_ALREADY_EXISTS = -2;
  public static final int RESULT_INVALID_CREDENTIALS = -3;
  public static final int RESULT_USER_DOES_NOT_EXIST = -4;


  // User types
  public enum UserType {
    USER(0),
    ADMIN(1);

    private final int value;
    UserType(final int value){
      this.value = value;
    }
    public int getValue(){
      return value;
    }
  }

  // Order status types
  public enum OrderStatus{
    IN_CART(0),
    ORDERED(1),
    DENIED(2);

    private final int value;
    OrderStatus(int value){
      this.value = value;
    }
    public int getValue(){
      return value;
    }
  }


  public static final int REVIEW_MESSAGE_LENGTH = 250; // max 250 character review
  public static final int ORDER_MAX_SUBMIT_ATTEMPTS = 3; // max 250 character review


}
