package com.sebastian.veterinaria_dolly.veterinaria_dolly.services.impl;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Category;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ResponseWrapper;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.services.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {


    @Override
    public ResponseWrapper<List<Category>> findAll() {
        //return List.of();
        return new ResponseWrapper<>(List.of(), "findAll desde el servicio de implementación");
    }

    @Override
    public ResponseWrapper<Category> findById(Long id) {
        return new ResponseWrapper<>(null, "findById desde el servicio de implementación");
    }

    @Override
    public ResponseWrapper<Category> create(Category category) {
        return new ResponseWrapper<>(null, "create desde el servicio de implementación");
    }

    @Override
    public ResponseWrapper<Category> update(Long id, Category category) {
        return new ResponseWrapper<>(null, "update desde el servicio de implementación");
    }

    @Override
    public ResponseWrapper<Category> delete(Long id) {
        return new ResponseWrapper<>(null, "delete desde el servicio de implementación");
    }
}
