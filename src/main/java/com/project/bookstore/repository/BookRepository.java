package com.project.bookstore.repository;

import com.project.bookstore.model.BookEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//Repository for CRUD operation
public interface BookRepository extends CrudRepository<BookEntity, String> {

    //Custom method to get 10 books at a time
    @Query(value = "Select * from BOOK limit 10 offset ?1",nativeQuery = true)
    List<BookEntity> findAllBook(Integer offset);

}
