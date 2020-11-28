package com.project.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bookstore.common.Util;
import com.project.bookstore.common.WConstants;
import com.project.bookstore.model.BookEntity;
import com.project.bookstore.service.BookService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    Logger log = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    //Controller to get all books based on page no. Accepts 'pageno' as a query parameter

    @GetMapping("/getAllBooks")
    public List<BookEntity> getAllBooks(@RequestParam(required = false, defaultValue = "1") Integer pageno) throws Exception {
        try {
            return bookService.getAllBooks(pageno);
        }catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /*
     * Controller to get all the categories
     * @return List of Category
     * */
    @GetMapping("/getAllCategory")
    public List<String> getAllCategory() throws Exception {
        try {
            return bookService.getAllCategory();
        }catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * @param bid
     * @return book entity as a JSON String (with indentation), otherwise error with status code and message
     * @apiNote for both client and partners
     */
    @RequestMapping(value = "/getProductInfo", method = RequestMethod.GET)
    public String getBookInfo(@RequestParam(name = "bid")String bid){
        log.debug(String.format("Entered getProductInfo for bid: %s", bid));
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

    /**
     *
     * @param title: book title/seach query
     * @return list of all books containing that title word
     */
    @RequestMapping(value = "/searchByTitle", method = RequestMethod.GET)
    public List<BookEntity> searchBooksByTitle(@RequestParam(name = "title") String title){
        log.debug(String.format("Entered searchBooksByTitle for title: %s", title));
        try{
            return bookService.searchBooksByTitle(title);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            return null;
        }
    }


}
