package com.sebastian.veterinaria_dolly.veterinaria_dolly.services.impl;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Category;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.create.CreateCategoryDto;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ResponseWrapper;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.repositories.CategoryRepository;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseWrapper<Category> create(CreateCategoryDto category) {

        //? Validemos que no se repita la categoría
        String categoryName = category.getName().trim().toUpperCase();
        Optional<Category> getCategoryOptional = categoryRepository.getCategoryByName(categoryName);

        if( getCategoryOptional.isPresent() )
            return new ResponseWrapper<>(null, "La categoría ya está registrada");

        //? Pasamos hasta acá, registramos categoría
        Category newCategory = new Category();
        newCategory.setName(categoryName);
        newCategory.setStatus(true); //* Por defecto entra en true
        newCategory.setUserCreated("usuario123"); //! Ajustar cuando se implemente Security
        newCategory.setDateCreated(new Date()); //! Ajustar cuando se implemente Security
        newCategory.setUserUpdated("usuario123"); //! Ajustar cuando se implemente Security
        newCategory.setDateUpdated(new Date()); //! Ajustar cuando se implemente Security

        return new ResponseWrapper<>(categoryRepository.save(newCategory), "Categoría guardada correctamente");

    }

    @Override
    public Page<Category> findAll(String search, Pageable pageable) {

        //* La especificación me permite condensar los parámetros de búsqueda y el criterio de búsqueda
        //* de esta manera sigo usando JPA embebido para implementar el código requerido
        Specification<Category> spec = this.searchByFilter(search);
        return categoryRepository.findAll(spec, pageable);

    }

    @Override
    public ResponseWrapper<Category> findById(Long id) {

        Optional<Category> productOptional = categoryRepository.findById(id);
        if( productOptional.isPresent() ){
            Category product = productOptional.orElseThrow();
            return new ResponseWrapper<>(product, "Categoría encontrada por ID correctamente");
        }

        return new ResponseWrapper<>(null, "La categoría no pudo ser encontrado por el ID");

    }

    @Override
    public ResponseWrapper<Category> update(Long id, Category category) {
        return new ResponseWrapper<>(null, "update desde el servicio Category de implementación");
    }

    @Override
    public ResponseWrapper<Category> delete(Long id) {
        return new ResponseWrapper<>(null, "delete desde el servicio Category de implementación");
    }

    //* Para el buscador de categoría.
    //? Buscarémos solo por el name (Nombre de categoría).
    public Specification<Category> searchByFilter(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), "%" + name.toUpperCase() + "%");
        };
    }
}

