package com.adobe.orderapp.service;

import com.adobe.orderapp.entity.Product;
import com.adobe.orderapp.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private ProductRepository productRepository; // created class is wired

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).get();
    }

    public Product addProduct(Product p) {
        return  productRepository.save(p);
    }
}
