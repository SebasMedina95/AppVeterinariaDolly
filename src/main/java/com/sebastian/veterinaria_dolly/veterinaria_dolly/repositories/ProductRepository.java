package com.sebastian.veterinaria_dolly.veterinaria_dolly.repositories;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
