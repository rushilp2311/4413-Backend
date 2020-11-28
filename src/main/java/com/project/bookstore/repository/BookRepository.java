package com.project.bookstore.repository;

import com.project.bookstore.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

//Repository for CRUD operation
public interface BookRepository extends CrudRepository<BookEntity, String> {

    //Custom method to get 10 books at a time
    @Query(value = "Select * from BOOK limit 10 offset ?1",nativeQuery = true)
    List<BookEntity> findAllBook(Integer offset);

    @Query(value = "select CATEGORY from book group by CATEGORY", nativeQuery = true)
    List<String> findAllCategory();

    @Query(value = "select * from book where bid = :bid", nativeQuery = true)
    BookEntity findBookEntityByBid(String bid);

    /* I've tried all ways of writing this query, it just doesn't work. DB2 SUCKS.
    * We're a group of 3 people and DB search is a "blue" requirement. I'll leave it for later */
    @Query(value = "select * from book where title = :title", nativeQuery = true)
    List<BookEntity> searchBooksByTitle(@Param(value = "title") String title);

    @Query(value = "select * from book", nativeQuery = true)
    List<BookEntity> getAllBooks(String title);
}
