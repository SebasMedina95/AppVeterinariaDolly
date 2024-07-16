package com.sebastian.veterinaria_dolly.veterinaria_dolly.repositories;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long>, JpaSpecificationExecutor<Plan> {

    @Query("SELECT p FROM Plan p WHERE UPPER(p.name) = UPPER(:planName)")
    Optional<Plan> getPlanByName(String planName);

    @Query("SELECT p FROM Plan p WHERE UPPER(p.name) = UPPER(:planName) AND p.id <> :id")
    Optional<Plan> getPlanByNameForEdit(String planName, Long id);


    @Query("SELECT p FROM Plan p LEFT JOIN FETCH p.plazas WHERE p.id = :id")
    Optional<Plan> getByIdWithPlazas(@Param("id") Long id);

}
