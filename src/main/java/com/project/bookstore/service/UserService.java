package com.project.bookstore.service;

import com.project.bookstore.common.Util;
import com.project.bookstore.common.WConstants;
import com.project.bookstore.model.UserEntity;
import com.project.bookstore.repository.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  public String singupUser(UserEntity userEntity){
    JSONObject json = new JSONObject();
    json.put("status", WConstants.RESPONSE_SUCCESS);

    if(!userEntity.isValid()){
      json.put("message", "Data invalid. Please try again.");
    }

    if(userRepository.isUserAlreadyExist(userEntity.getEmail())){
      json.put("message", "User already exists. Please try logging in.");
      return json.toString();
    }

    if(userRepository.signupUser(userEntity) == 1){
      json.put("message", "Sign up successful for user: " + userEntity.getEmail());
      return json.toString();
    }

    return Util.getJsonResponse(WConstants.RESULT_UNKNOWN_ERROR, null);
  }

}
