package com.project.bookstore.service;

import com.project.bookstore.model.BookEntity;
import com.project.bookstore.repository.BookRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class BookService {

    org.slf4j.Logger log = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepository bookRepository;

    public List<BookEntity> getAllBooks(Integer offset) throws Exception {
        if (offset > 0) {
            List<BookEntity> books = new ArrayList<>();
            try {
                books = bookRepository.findAllBook((offset - 1) * 10);

            }catch (Exception e) {
                throw new Exception(e);
            }
            return books;
        }
        else {
            log.error("Page no. cannot be 0");
            return new ArrayList<>();
        }
    }

    public List<BookEntity> getBooksByCategory(String category) throws Exception{
        if(StringUtils.isEmpty(category)){
            return new ArrayList<>();
        }
        try{
            return bookRepository.getBooksByCategory(category);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public List<String> getAllCategory() throws Exception {
        List<String> categories;
        try {
            categories = bookRepository.findAllCategory();
        }
        catch(Exception e) {
            throw new Exception(e);
        }
        return categories;
    }

    public BookEntity getBookInfo(int bid){
        if(bid < 0){
            return null;
        }
        return bookRepository.findBookEntityByBid(bid);
    }


    public List<BookEntity> searchBooksByTitle(String title) throws Exception{
        List<BookEntity> books;
        try{
             books = bookRepository.searchBooksByTitle(title);
        } catch (Exception e){
            throw new Exception(e);
        }
        return books;
    }

}
