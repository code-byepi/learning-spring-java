package com.app.springbootcrud.services;

import com.app.springbootcrud.entities.Product;

import java.util.List;
import java.util.Optional;
import java.util.function.LongFunction;

public interface ProductService {

    List<Product> findAll();
    Optional<Product> findById(Long id);

    Product save(Product product);
    Optional<Product> update(Long id, Product product);

    Optional<Product> delete(Product product);



}
