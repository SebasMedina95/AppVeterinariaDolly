package com.sebastian.veterinaria_dolly.veterinaria_dolly.repositories;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

//* PARA EL FILTRADOR, REQUERIMOS EXTENDER TAMBIÉN DE JpaSpecificationExecutor<Category>
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    @Query("SELECT c FROM Category c WHERE c.name = :categoryName")
    Optional<Category> getCategoryByName(String categoryName);

}
