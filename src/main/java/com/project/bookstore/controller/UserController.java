package com.project.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bookstore.common.Util;
import com.project.bookstore.common.WConstants;
import com.project.bookstore.model.*;
import com.project.bookstore.service.UserService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

  Logger log = LoggerFactory.getLogger(UserController.class);

  @Autowired
  UserService userService;

  @RequestMapping(value = "/signup", method = RequestMethod.POST)
  public String signup(@RequestBody String data){
    log.debug(String.format("Entered user signup for data: %s", data));

    try {
      ObjectMapper mapper = new ObjectMapper();
      UserSignupInputData inputData = mapper.readValue(data, UserSignupInputData.class);
      UserEntity user = new UserEntity(null, inputData.getFirstName(), inputData.getLastName(),
              inputData.getEmail(), inputData.getPassword(), 0, null);
      return userService.singupUser(user);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return Util.getJsonResponse(WConstants.RESULT_UNKNOWN_ERROR, null);
    }

  }


  /*
    ---        Admin credentials
    ---        email:   admin@admin.com
    ---        pwd:     admin123
   */
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public String login(@RequestBody String data){
    log.debug(String.format("Entered user /login for: %s", data));

    try{
      ObjectMapper mapper = new ObjectMapper();
      UserLoginInputData inputData = mapper.readValue(data, UserLoginInputData.class);
      return userService.loginUser(inputData);
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return Util.getJsonResponse(WConstants.RESULT_UNKNOWN_ERROR, null);
    }
  }

  @RequestMapping(value = "/addAddress", method = RequestMethod.POST)
  public String addAddress(@RequestBody String data){
    log.debug(String.format("Entered /addAddress for: %s", data));

    try{
      ObjectMapper mapper = new ObjectMapper();
      AddressInputData inputData = mapper.readValue(data, AddressInputData.class);
      return userService.addUserAddress(inputData);
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return Util.getJsonResponse(WConstants.RESULT_UNKNOWN_ERROR, null);
    }
  }

  @RequestMapping(value = "/getAddress", method = RequestMethod.GET)
  public String getAddress(@RequestParam String userId){
    log.debug(String.format("Entered /getAddress for: %s", userId));

    try{
      return userService.getAddress(userId);
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return Util.getJsonResponse(WConstants.RESULT_UNKNOWN_ERROR, null);
    }
  }
}
