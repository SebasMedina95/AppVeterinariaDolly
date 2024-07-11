package com.sebastian.veterinaria_dolly.veterinaria_dolly.services;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Supplier;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ResponseWrapper;

import java.util.List;

public interface SupplierService {

    ResponseWrapper<List<Supplier>> findAll();
    ResponseWrapper<Supplier> findById(Long id);
    ResponseWrapper<Supplier> create(Supplier supplier);
    ResponseWrapper<Supplier> update(Long id, Supplier supplier);
    ResponseWrapper<Supplier> delete(Long id);

}
