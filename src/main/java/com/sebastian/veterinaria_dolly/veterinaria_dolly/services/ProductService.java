package com.sebastian.veterinaria_dolly.veterinaria_dolly.services;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Product;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.create.CreateProductDto;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ResponseWrapper;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ResponseWrapper<List<Product>> findAll();
    ResponseWrapper<Product> findById(Long id);
    ResponseWrapper<Product> create(CreateProductDto product) throws IOException;
    ResponseWrapper<Product> update(Long id, Product product);
    ResponseWrapper<Product> delete(Long id);

}
