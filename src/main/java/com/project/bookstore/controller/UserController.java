package com.project.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bookstore.common.Util;
import com.project.bookstore.common.WConstants;
import com.project.bookstore.model.UserEntity;
import com.project.bookstore.service.UserService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  Logger log = LoggerFactory.getLogger(UserController.class);

  @Autowired
  UserService userService;

  @RequestMapping(value = "/signup", method = RequestMethod.POST)
  public String signup(@RequestParam (name = "firstName") String first_name, @RequestParam(name = "lastName") String last_name,
                       @RequestParam (name = "email") String email, @RequestParam (name = "password") String password){
    log.debug(String.format("Entered user signup for user: %s, email: %s", first_name, email));

    try {
      UserEntity user = new UserEntity(null, first_name, last_name, email, password, 0);
      return userService.singupUser(user);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return Util.getJsonResponse(WConstants.RESULT_UNKNOWN_ERROR, null);
    }

  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public String login(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password){
    log.debug(String.format("Entered user signup for: %s", email));

    try{
      return userService.loginUser(email, password);
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return Util.getJsonResponse(WConstants.RESULT_UNKNOWN_ERROR, null);
    }
  }

}
