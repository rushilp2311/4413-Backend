package com.project.bookstore.service;

import com.project.bookstore.model.Book;
import com.project.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks(Integer offset) throws Exception {
        if (offset > 0) {

            List<Book> books = new ArrayList<>();
            try {
                books = bookRepository.findAllBook((offset - 1) * 10);

            }catch (Exception e) {
                throw new Exception(e);
            }
            return books;
        }
        else
        {
            Logger LOGGER = Logger.getLogger(BookService.class.getName());
            LOGGER.log(Level.SEVERE,"Page no cannot be 0");
            return new ArrayList<>();
        }
    }
}
