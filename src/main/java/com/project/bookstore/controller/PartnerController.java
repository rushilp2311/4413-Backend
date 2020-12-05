package com.project.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bookstore.common.Util;
import com.project.bookstore.common.WConstants;
import com.project.bookstore.model.BookEntity;
import com.project.bookstore.service.BookService;
import com.project.bookstore.service.OrderService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;

@RestController
@RequestMapping("/rest")
public class PartnerController {

  Logger log = LoggerFactory.getLogger(BookController.class);

  @Autowired
  private BookService bookService;
  @Autowired
  private OrderService orderService;

  /**
   * -- REST FUNCTIONALITY FOR PARTNERS
   * @param bid - book id
   * @return book entity as a JSON String (with indentation), otherwise error with status code and message
   * @apiNote for both client and partners
   */
  @GetMapping(value = "/getProductInfo", produces = MediaType.APPLICATION_JSON_VALUE)
  public String getBookInfo(@RequestParam(name = "bid") int bid){
    log.debug(String.format("Entered getProductInfo (REST) for bid: %s", bid));
    ObjectMapper mapper = new ObjectMapper();
    try{
      JSONObject json = new JSONObject();
      BookEntity book = bookService.getBookInfo(bid);
      if(book == null){
        json.put("status", WConstants.RESPONSE_FAIL);
        json.put("message", "Please enter a valid book/product ID.");
        return json.toString(4);
      }
      return mapper.writeValueAsString(book);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return Util.getJsonResponse(WConstants.RESULT_UNKNOWN_ERROR, null);
    }
  }

  @GetMapping(value = "/getOrdersByPartNumber", produces = MediaType.APPLICATION_JSON_VALUE)
  public String getOrdersByPartNumber(@RequestParam(name = "bid") int bid){
    log.debug(String.format("Entered getOrdersByPartNumber (REST) for bid: %s", bid));
    ObjectMapper mapper = new ObjectMapper();
    try{
      JSONObject json = new JSONObject();
      BookEntity book = bookService.getBookInfo(bid);
      if(book == null){
        json.put("status", WConstants.RESPONSE_FAIL);
        json.put("message", "Please enter a valid book/product ID.");
        return json.toString(4);
      }

      return orderService.getOrdersByBid(bid);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return Util.getJsonResponse(WConstants.RESULT_UNKNOWN_ERROR, null);
    }
  }

}
