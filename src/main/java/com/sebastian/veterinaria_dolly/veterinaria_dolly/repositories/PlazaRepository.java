package com.sebastian.veterinaria_dolly.veterinaria_dolly.repositories;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Plaza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlazaRepository extends JpaRepository<Plaza, Long>, JpaSpecificationExecutor<Plaza> {

    @Query("SELECT p FROM Plaza p WHERE UPPER(p.name) = UPPER(:plazaName)")
    Optional<Plaza> getPlazaByName(String plazaName);

}
