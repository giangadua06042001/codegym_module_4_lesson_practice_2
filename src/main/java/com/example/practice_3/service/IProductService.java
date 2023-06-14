package com.example.practice_3.service;

import com.example.practice_3.model.Product;

import java.util.Optional;

public interface IProductService {
    void save(Product product);
    Iterable<Product> findAll();
    Optional<Product> findById(Long id);
}