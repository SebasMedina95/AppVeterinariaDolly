package com.sebastian.veterinaria_dolly.veterinaria_dolly.repositories;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PlanRepository extends JpaRepository<Plan, Long>, JpaSpecificationExecutor<Plan> {
}
