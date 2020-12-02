package com.project.bookstore.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bookstore.common.Util;
import com.project.bookstore.common.WConstants;
import com.project.bookstore.model.CartInputData;
import com.project.bookstore.model.CartItemInputData;
import com.project.bookstore.model.InputData;
import com.project.bookstore.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/order")
public class OrderController {

  Logger log = LoggerFactory.getLogger(BookController.class);

  @Autowired
  private OrderService orderService;


  @PostMapping("/addSingleCartItem")
  public String addSingleItemToCart(@RequestBody String data){
    log.debug("Entered /addSingleItemToCart with data: " +  data);
    if(StringUtils.isEmpty(data)){
      return Util.getJsonResponse(WConstants.RESULT_INVALID_DATA, null);
    }

    ObjectMapper mapper = new ObjectMapper();
    try {
      CartItemInputData cartData = mapper.readValue(data, CartItemInputData.class);
      return orderService.addSingleItemToCart(cartData);
    } catch (JsonProcessingException e) {
      log.error(e.getMessage(), e);
      return Util.getJsonResponse(WConstants.RESULT_UNKNOWN_ERROR, null);
    }
  }

  /**
   * Add bulk items to cart
   * @param data - userId, List of type <OrderDetailEntity.class>
   * @return successful status + message
   */
  @PostMapping("/addItemsToCart")
  public String addToCart(@RequestBody String data){
    log.debug("Entered /addToCart with data: " +  data);
    if(StringUtils.isEmpty(data)){
      return Util.getJsonResponse(WConstants.RESULT_INVALID_DATA, null);
    }

    ObjectMapper mapper = new ObjectMapper();
    try {
      CartInputData cartData = mapper.readValue(data, CartInputData.class);
      return orderService.addItemListToCart(cartData);
    } catch (JsonProcessingException e) {
      log.error(e.getMessage(), e);
      return Util.getJsonResponse(WConstants.RESULT_UNKNOWN_ERROR, null);
    }
  }

  /**
   * @param data - userId of the user
   * @return list of cart items
   */
  @GetMapping("/getCart")
  public String getCartItems(@RequestBody String data){
    log.debug("Entered /getCartItems with data: " + data);
    if(StringUtils.isEmpty(data)){
      return Util.getJsonResponse(WConstants.RESULT_INVALID_DATA, null);
    }

    ObjectMapper mapper = new ObjectMapper();
    try {
      InputData inputData = mapper.readValue(data, InputData.class);
      return orderService.getCartItems(inputData.getUserId());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return Util.getJsonResponse(WConstants.RESULT_UNKNOWN_ERROR, null);
    }
  }

}
