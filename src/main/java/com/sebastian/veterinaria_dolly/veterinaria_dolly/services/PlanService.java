package com.sebastian.veterinaria_dolly.veterinaria_dolly.services;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Plan;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.create.CreatePlanDto;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.update.UpdatePlanDto;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ResponseWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlanService {

    ResponseWrapper<Plan> create(CreatePlanDto service);
    Page<Plan> findAll(String search, Pageable pageable);
    ResponseWrapper<Plan> findById(Long id);
    ResponseWrapper<Plan> update(Long id, UpdatePlanDto service);
    ResponseWrapper<Plan> delete(Long id);

}
