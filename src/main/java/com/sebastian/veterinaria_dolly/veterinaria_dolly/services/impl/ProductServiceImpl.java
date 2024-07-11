package com.sebastian.veterinaria_dolly.veterinaria_dolly.services.impl;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Product;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ResponseWrapper;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.services.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    @Override
    public ResponseWrapper<List<Product>> findAll() {
        //return List.of();
        return new ResponseWrapper<>(List.of(), "findAll desde el servicio de implementación");
    }

    @Override
    public ResponseWrapper<Product> findById(Long id) {
        return new ResponseWrapper<>(null, "findById desde el servicio de implementación");
    }

    @Override
    public ResponseWrapper<Product> create(Product product) {
        return new ResponseWrapper<>(null, "create desde el servicio de implementación");
    }

    @Override
    public ResponseWrapper<Product> update(Long id, Product product) {
        return new ResponseWrapper<>(null, "update desde el servicio de implementación");
    }

    @Override
    public ResponseWrapper<Product> delete(Long id) {
        return new ResponseWrapper<>(null, "delete desde el servicio de implementación");
    }
}
