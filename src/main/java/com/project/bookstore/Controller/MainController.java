package com.project.bookstore.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

  @RequestMapping("/")
  public String home(){
    return hello();
  }

  @RequestMapping(value = "/hello")
  public String hello(){
    return "hello yo";
  }

}
