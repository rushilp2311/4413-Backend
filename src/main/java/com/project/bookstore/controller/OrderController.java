package com.project.bookstore.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bookstore.common.Util;
import com.project.bookstore.common.WConstants;
import com.project.bookstore.model.CartInputData;
import com.project.bookstore.service.BookService;
import com.project.bookstore.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

  Logger log = LoggerFactory.getLogger(BookController.class);

  @Autowired
  private OrderService orderService;


  @PostMapping("/addCart")
  public String addToCart(@RequestBody String data){
    log.debug("Entered /addToCart with data: " +  data);
    if(StringUtils.isEmpty(data)){
      return Util.getJsonResponse(WConstants.RESULT_INVALID_DATA, null);
    }

    ObjectMapper mapper = new ObjectMapper();
    try {
      CartInputData cartData = mapper.readValue(data, CartInputData.class);
      log.debug("\n userId: " + cartData.getUserId());

      String res = orderService.addCart(cartData);
      return "Added to cart";
    } catch (JsonProcessingException e) {
      log.error(e.getMessage(), e);
      return Util.getJsonResponse(WConstants.RESULT_UNKNOWN_ERROR, null);
    }
  }

}
