package com.adobe.demo.dao;

import org.springframework.stereotype.Repository;

@Repository
public class BookRepoDbImpl implements  BookRepo{
    @Override
    public void addBook() {
        System.out.println("Book in database!!!");
    }
}
