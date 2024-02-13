package com.gmat.springboot.crud.springbootcrud.repositories;

import org.springframework.data.repository.CrudRepository;

import com.gmat.springboot.crud.springbootcrud.entities.Product;

public interface ProductRepository extends CrudRepository< Product, Long > {
    
}
