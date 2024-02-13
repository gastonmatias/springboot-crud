package com.gmat.springboot.crud.springbootcrud.services;

import java.util.List;
import java.util.Optional;

import com.gmat.springboot.crud.springbootcrud.entities.Product;

public interface ProductService {

    List<Product> findAll();
    Optional<Product> findById(Long id);
    Product save(Product product);
    Optional<Product> delete(Product product);
}
