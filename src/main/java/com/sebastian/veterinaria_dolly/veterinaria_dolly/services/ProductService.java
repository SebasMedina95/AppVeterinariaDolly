package com.sebastian.veterinaria_dolly.veterinaria_dolly.services;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Product;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ResponseWrapper;

import java.util.List;

public interface ProductService {

    ResponseWrapper<List<Product>> findAll();
    ResponseWrapper<Product> findById(Long id);
    ResponseWrapper<Product> create(Product product);
    ResponseWrapper<Product> update(Long id, Product product);
    ResponseWrapper<Product> delete(Long id);

}
