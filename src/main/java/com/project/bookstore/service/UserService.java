package com.project.bookstore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bookstore.common.Util;
import com.project.bookstore.common.WConstants;
import com.project.bookstore.model.*;
import com.project.bookstore.repository.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;
  @Autowired
  PasswordEncoder encoder;

  public String singupUser(UserEntity userEntity){
    JSONObject json = new JSONObject();

    if(!userEntity.isValid()){
      return Util.getJsonResponse(WConstants.INVALID_USER_SIGNUP_DATA, null);
    }

    if(userRepository.isUserEmailExist(userEntity.getEmail())){
      json.put("status", WConstants.RESPONSE_FAIL);
      json.put("message", "User already exists. Please try logging in.");
      return json.toString();
    }

    userEntity.setPassword(encoder.encode(userEntity.getPassword()));
    if(userRepository.signupUser(userEntity) == 1){
      json.put("status", WConstants.RESPONSE_SUCCESS);
      json.put("message", "Sign up successful for user: " + userEntity.getEmail());
      return json.toString();
    }

    return Util.getJsonResponse(WConstants.RESULT_UNKNOWN_ERROR, null);
  }

  public String loginUser(UserLoginInputData data){
    JSONObject json = new JSONObject();

    UserEntity user = userRepository.findUser(data.getEmail());
    if(user == null){ // if user not found
      return Util.getJsonResponse(WConstants.RESULT_USER_DOES_NOT_EXIST, data.getEmail());
    }
    if(!encoder.matches(data.getPassword(), user.getPassword())){ // if user found but incorrect password is entered
      return Util.getJsonResponse(WConstants.RESULT_INVALID_CREDENTIALS, user.getUser_id());
    }
    json.put("userType", user.getUser_type());
    json.put("firstName", user.getFirst_name());
    json.put("lastName", user.getLast_name());
    json.put("userId", user.getUser_id());
    json.put("status", WConstants.RESPONSE_SUCCESS);
    return json.toString();
  }

  public boolean isUserExist(String userId){
    return userRepository.findUserByUserId(userId) != null;
  }


  public String addUserAddress(AddressInputData data){
    if(!this.isUserExist(data.getUserId())){
      return Util.getJsonResponse(WConstants.RESULT_USER_DOES_NOT_EXIST, data.getUserId());
    }

    if(userRepository.addUserAddress(data) != 1){
      Util.getJsonResponse(WConstants.RESULT_UNKNOWN_ERROR, data.getUserId());
    }
    JSONObject json = new JSONObject();
    json.put("status", WConstants.RESPONSE_SUCCESS);
    json.put("message", "Address successfully saved.");
    return json.toString(4);
  }

  public String getAddress(String userId){
    if(!this.isUserExist(userId)){
      return Util.getJsonResponse(WConstants.RESULT_USER_DOES_NOT_EXIST, userId);
    }

    AddressEntity address = userRepository.getAddress(userId);
    JSONObject json = new JSONObject();
    if(address == null){
      json.put("status", WConstants.RESPONSE_FAIL);
      json.put("message", "No saved address. Please add one.");
      return json.toString(4);
    }

    json = new JSONObject(address);
    return json.toString();

//    return json.toString(4);
  }

}
