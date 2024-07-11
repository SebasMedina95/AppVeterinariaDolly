package com.sebastian.veterinaria_dolly.veterinaria_dolly.services;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Category;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ResponseWrapper;

import java.util.List;

public interface CategoryService {

    ResponseWrapper<List<Category>> findAll();
    ResponseWrapper<Category> findById(Long id);
    ResponseWrapper<Category> create(Category category);
    ResponseWrapper<Category> update(Long id, Category category);
    ResponseWrapper<Category> delete(Long id);

}
