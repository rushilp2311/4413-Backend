package com.project.bookstore.service;

import com.project.bookstore.model.Book;
import com.project.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks(Integer offset) {
        List<Book> books = new ArrayList<>();
        books = bookRepository.findAllBook((offset - 1)*10);
          return books;
    }
}
