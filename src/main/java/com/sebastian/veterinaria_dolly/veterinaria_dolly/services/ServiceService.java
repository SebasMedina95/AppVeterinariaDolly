package com.sebastian.veterinaria_dolly.veterinaria_dolly.services;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Service;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.create.CreateServiceDto;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.update.UpdateServiceDto;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ResponseWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServiceService {

    ResponseWrapper<Service> create(CreateServiceDto service);
    Page<Service> findAll(String search, Pageable pageable);
    ResponseWrapper<Service> findById(Long id);
    ResponseWrapper<Service> update(Long id, UpdateServiceDto service);
    ResponseWrapper<Service> delete(Long id);

}
