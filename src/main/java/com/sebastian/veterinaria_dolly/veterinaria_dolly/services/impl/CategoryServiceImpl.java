package com.sebastian.veterinaria_dolly.veterinaria_dolly.services.impl;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Category;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.create.CreateCategoryDto;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.update.UpdateCategoryDto;
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

    static String dummiesUser = "usuario123";
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
        newCategory.setUserCreated(dummiesUser); //! Ajustar cuando se implemente Security
        newCategory.setDateCreated(new Date()); //! Ajustar cuando se implemente Security
        newCategory.setUserUpdated(dummiesUser); //! Ajustar cuando se implemente Security
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
    public ResponseWrapper<Category> update(Long id, UpdateCategoryDto category) {

        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if( categoryOptional.isPresent() ){

            Category categoryDb = categoryOptional.orElseThrow();

            //? Validemos que no se repita la categoría
            String categoryName = category.getName().trim().toUpperCase();
            Optional<Category> getCategoryOptional = categoryRepository.getCategoryByName(categoryName);

            if( getCategoryOptional.isPresent() )
                return new ResponseWrapper<>(null, "La categoría ya está registrada");

            //? Vamos a actualizar si llegamos hasta acá
            categoryDb.setName(categoryName);
            categoryDb.setUserUpdated(dummiesUser);
            categoryDb.setDateUpdated(new Date());

            return new ResponseWrapper<>(categoryRepository.save(categoryDb), "Categoría Actualizada Correctamente");

        }else{

            return new ResponseWrapper<>(null, "La Categoría del producto no fue encontrada");

        }

    }

    @Override
    public ResponseWrapper<Category> delete(Long id) {

        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if( categoryOptional.isPresent() ){

            Category categoryDb = categoryOptional.orElseThrow();

            //? Vamos a actualizar si llegamos hasta acá
            //? ESTO SERÁ UN ELIMINADO LÓGICO!
            categoryDb.setStatus(false);
            categoryDb.setUserUpdated("usuario123");
            categoryDb.setDateUpdated(new Date());

            return new ResponseWrapper<>(categoryRepository.save(categoryDb), "Categoría Eliminada Correctamente");

        }else{

            return new ResponseWrapper<>(null, "La Categoría del producto no fue encontrada");

        }

    }

    //* Para el buscador de categoría.
    //? Buscarémos solo por el name (Nombre de categoría).
    //? NOTA: No olvidar el status.
    public Specification<Category> searchByFilter(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return criteriaBuilder.isTrue(root.get("status"));
            }
            return criteriaBuilder.and(
                    criteriaBuilder.isTrue(root.get("status")),
                    criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), "%" + name.toUpperCase() + "%")
            );
        };
    }
}

