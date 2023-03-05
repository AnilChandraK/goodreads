package com.example.goodreads.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.*;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

import com.example.goodreads.model.Book;
import com.example.goodreads.model.BookRowMapper;

import com.example.goodreads.repository.BookRepository;
@Service
public class BookH2Service implements BookRepository{
    @Autowired
    private JdbcTemplate db;
    @Override
    public ArrayList<Book> getBooks(){
        List<Book> bookList = db.query("select* from book", new BookRowMapper());
        ArrayList<Book> books = new ArrayList<>(bookList);
        return books;
    }
    

    @Override
    public Book getBookById(int bookId){
        String sql = "select * from book where id = ?" ;
        Book book = db.queryForObject(sql, new BookRowMapper(), bookId);
        return book;
    }
    
    
    @Override
    public Book addBook(Book book){
        db.update("insert into book(name,imageUrl) values (?,?)",book.getName(), book.getImageUrl());
        return db.queryForObject("select * from book where name = ? and imageUrl = ?", new BookRowMapper(), book.getName(), book.getImageUrl());
    }
    
    @Override
    public Book updateBook(int bookId, Book book){
        if(book.getImageUrl()!=null){
            db.update("update book set imageUrl = ? where id =?", book.getImageUrl(),bookId);
            }
        if(book.getName()!=null){
            db.update("update book set name = ? where id =?", book.getName(),bookId);
            }
        return getBookById(bookId);
    }
    
    @Override
    void deleteBook(int bookId){
        db.update("delete from book where id = ?", bookId);
    }

}
