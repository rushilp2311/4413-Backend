package com.project.bookstore.controller;

import com.project.bookstore.model.Book;
import com.project.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    //Controller to get all books based on page no. Accepts 'pageno' as a query parameter

    @GetMapping("/getAllBooks")
    public List<Book> getAllBooks(@RequestParam(required = false) Integer pageno) throws Exception {
        try {
            return  bookService.getAllBooks(pageno);
        }catch (Exception e) {

            throw new Exception(e);
        }
    }
}
