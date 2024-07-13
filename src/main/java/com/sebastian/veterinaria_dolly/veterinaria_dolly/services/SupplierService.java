package com.sebastian.veterinaria_dolly.veterinaria_dolly.services;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Supplier;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.create.CreateSupplierDto;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.update.UpdateSupplierDto;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ResponseWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SupplierService {

    ResponseWrapper<Supplier> create(CreateSupplierDto category);
    Page<Supplier> findAll(String search, Pageable pageable);
    ResponseWrapper<Supplier> findById(Long id);
    ResponseWrapper<Supplier> update(Long id, UpdateSupplierDto supplier);
    ResponseWrapper<Supplier> delete(Long id);

}
