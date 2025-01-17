package com.adobe.demo.service;

import com.adobe.demo.dao.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AppService {
    @Autowired
    private BookRepo bookRepo; // not tightly coupled
    // any implementation of BookRepo can be injected, DI by type

    public void insert() {
        this.bookRepo.addBook();
    }
}
