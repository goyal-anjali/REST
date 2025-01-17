package com.adobe.demo.dao;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
//@Profile("dev")
@ConditionalOnMissingBean( name = "bookRepoDbImpl")
public class BookRepoMongoImpl implements BookRepo{
    @Override
    public void addBook() {
        System.out.println("Mongo store!!1");
    }
}
