package com.sebastian.veterinaria_dolly.veterinaria_dolly.repositories;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE UPPER(p.name) = UPPER(:name) OR UPPER(p.slug) = LOWER(:slug)")
    Optional<Product> getProductByNameAndBySlug(String name, String slug);

}
