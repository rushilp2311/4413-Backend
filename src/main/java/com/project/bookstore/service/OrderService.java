package com.project.bookstore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bookstore.common.Util;
import com.project.bookstore.common.WConstants;
import com.project.bookstore.model.*;
import com.project.bookstore.repository.OrderRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

  @Autowired
  UserService userService;
  @Autowired
  OrderRepository orderRepository;

  public String addSingleItemToCart(CartItemInputData data){
    // make sure that user exists
    if(!userService.isUserExist(data.getUserId())){
      Util.getJsonResponse(WConstants.RESULT_USER_DOES_NOT_EXIST, data.getUserId());
    }
    OrderEntity order = this.returnCartOrCreate(data.getUserId());

    // create OrderDetailEntity from cartItem data
    List<OrderDetailEntity> list = new ArrayList<>();
    list.add(new OrderDetailEntity(order.getOrderId(), data.getBid(), data.getQuantity(), data.getPrice()));

    if(orderRepository.addCartItems(order.getOrderId(), list) != 1){
      return Util.getJsonResponse(WConstants.RESULT_UNKNOWN_ERROR, data.getUserId());
    }

    JSONObject json = new JSONObject();
    json.put("status", WConstants.RESPONSE_SUCCESS);
    json.put("message", "Item added to cart!");
    return json.toString();
  }

  public String addItemListToCart(CartInputData cartData){
    JSONObject json = new JSONObject();
    // make sure that user exists
    if(!userService.isUserExist(cartData.getUserId())) {
      Util.getJsonResponse(WConstants.RESULT_USER_DOES_NOT_EXIST, cartData.getUserId());
    }
    OrderEntity order = this.returnCartOrCreate(cartData.getUserId());

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

  // If the user has an existing cart, it will return it. Else, it will make a new one and return
  public OrderEntity returnCartOrCreate(String userId){
    OrderEntity order = orderRepository.findCartByUserId(userId);
    if(order == null){
      order = orderRepository.insertNewCartAndReturn(userId);
    }
    return order;
  }

  public String getCartItems(String userId) {
    JSONObject json = new JSONObject();
    OrderEntity order = orderRepository.findCartByUserId(userId);
    if (order == null){
      json.put("status", WConstants.RESPONSE_SUCCESS);
      json.put("message", "Cart is empty! Maybe go shop around and add some items?");
      return json.toString();
    }

    // fetch cart items & calculate total
    List<CartItem> cartItems = orderRepository.returnCartData(order.getOrderId());
    double cartTotal = 0;
    for (CartItem cartItem : cartItems) {
      cartTotal += cartItem.getAmount();
    }
    json.put("status", WConstants.RESPONSE_SUCCESS);
    json.put("cartTotal", Util.roundDouble(cartTotal));
    json.put("cartItems", cartItems);
    return json.toString();
  }

  public String removeCartItem(RemoveItemInputData data) {
    JSONObject json = new JSONObject();
    OrderEntity order = orderRepository.findCartByUserId(data.getUserId());

    // remove cart item
    if(orderRepository.removeCartItem(order.getOrderId(), data.getBid()) != 1){
      return Util.getJsonResponse(WConstants.RESULT_UNKNOWN_ERROR, data.getUserId());
    }

    json.put("status", WConstants.RESPONSE_SUCCESS);
    json.put("message", "Item successfully removed.");
    return json.toString();
  }

  public String confirmOrder(CreditCardInputData data) {
    JSONObject json = new JSONObject();
    json.put("status", WConstants.RESPONSE_FAIL);

    OrderEntity order = orderRepository.findCartByUserId(data.getUserId());
    if(order == null){
      json.put("message", "Cart is empty. Please try again later.");
      return json.toString();
    }

    // Check number of order submit attempts. If > 3, put order in Denied Status
    if (order.getSubmit_attempts() >= WConstants.ORDER_MAX_SUBMIT_ATTEMPTS){
      json.put("error", -1);
      json.put("message", "Credit Card Authorization Failed.");
      return json.toString(4);
    }

    // Payment Details validation
    if(!data.isValid()){
      StringBuilder errorMsg = new StringBuilder("Error: ");
      if(!data.isNameValid()){
        errorMsg.append("Name INVALID (max 30 characters)");
      }
      if (!data.isNumberValid()){
        errorMsg.append("Credit card number INVALID. ");
      }
      if(!data.isExpiryValid()){
        errorMsg.append("Expiry date INVALID. ");
      }
      if(!data.isCvvValid()){
        errorMsg.append("CVV INVALID. ");
      }
      json.put("message", errorMsg.toString());

      // increment submit_attempts for order
      int res = orderRepository.incrementSubmitAttempts(data.getUserId(), order.getOrderId());
      return json.toString(4);
    }

    // Submit order
    if(orderRepository.submitOrder(data.getUserId(), order.getOrderId()) != 1){
      return Util.getJsonResponse(WConstants.RESULT_UNKNOWN_ERROR, data.getUserId());
    }

    json.put("status", WConstants.RESPONSE_SUCCESS);
    json.put("message", "Order Successfully Completed.");
    json.put("orderId", "0000000" + order.getOrderId());
    return json.toString(4);
  }

  public String getOrdersByBid(int bid){
    List<OrderProcessedData> list = orderRepository.returnOrderByBid(bid);
    JSONObject mainJson = new JSONObject();

    if(list == null || list.isEmpty()){
      mainJson.put("status_code", WConstants.RESPONSE_SUCCESS);
      mainJson.put("status", "Success");
      mainJson.put("message", "No orders for this book yet!");
      return mainJson.toString(4);
    }

    mainJson.put("TITLE", list.get(0).getTitle());
    mainJson.put("PRICE", list.get(0).getPrice());

    JSONArray ordersArray = new JSONArray();
    for(OrderProcessedData item: list){
      JSONObject order = new JSONObject();
      order.put("ORDER_ID", item.getOrderId());
      order.put("ORDER_DATE", item.getOrderDate());
      order.put("USER_ID", item.getUserId());
      order.put("QUANTITY_BOUGHT", item.getQuantity());
      ordersArray.put(order);
    }

    mainJson.put("ORDERS", ordersArray);

    return mainJson.toString(4);

  }

}















