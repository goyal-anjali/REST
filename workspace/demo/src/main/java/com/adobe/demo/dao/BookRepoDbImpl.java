package com.adobe.demo.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository

//@Profile("prod")
public class BookRepoDbImpl implements  BookRepo{
    @Override
    public void addBook() {
        System.out.println("Book in database!!!");
    }
}
