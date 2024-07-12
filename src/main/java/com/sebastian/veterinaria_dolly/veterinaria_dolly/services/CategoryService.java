package com.sebastian.veterinaria_dolly.veterinaria_dolly.services;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Category;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.create.CreateCategoryDto;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ResponseWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    ResponseWrapper<Category> create(CreateCategoryDto category);
    Page<Category> findAll(String search, Pageable pageable);
    ResponseWrapper<Category> findById(Long id);
    ResponseWrapper<Category> update(Long id, Category category);
    ResponseWrapper<Category> delete(Long id);

}
