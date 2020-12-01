package com.project.bookstore.service;

import com.project.bookstore.common.Util;
import com.project.bookstore.common.WConstants;
import com.project.bookstore.model.CartInputData;
import com.project.bookstore.model.OrderEntity;
import com.project.bookstore.repository.OrderRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  @Autowired
  UserService userService;
  @Autowired
  OrderRepository orderRepository;

  public String addCart(CartInputData cartData){
    JSONObject json = new JSONObject();
    // make sure that user exists
    if(!userService.isUserExist(cartData.getUserId())){
      Util.getJsonResponse(WConstants.RESULT_USER_DOES_NOT_EXIST, cartData.getUserId());
    }
    // Find existing cart, if user has it. Otherwise, make a new cart.
    OrderEntity order = orderRepository.findCartByUserId(cartData.getUserId());
    if(order == null){
      order = orderRepository.insertNewCartAndReturn(cartData.getUserId());
    }
    // Add items to the cart (order)
    int res = orderRepository.addCartItems(order.getOrderId(), cartData.getItemList());
    if(res >= 1){
      json.put("status", WConstants.RESPONSE_SUCCESS);
      json.put("message", "Items have been added to cart!");
      return json.toString();
    } else if(res == 0){ // error checking, unreachable case
      json.put("status", WConstants.RESPONSE_SUCCESS);
      json.put("message", "No items were added to the cart.");
      return json.toString();
    } else{
      return Util.getJsonResponse(WConstants.RESULT_UNKNOWN_ERROR, cartData.getUserId());
    }
  }
}
