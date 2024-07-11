package com.sebastian.veterinaria_dolly.veterinaria_dolly.services.impl;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Supplier;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ResponseWrapper;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.services.SupplierService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Override
    public ResponseWrapper<List<Supplier>> findAll() {
        return new ResponseWrapper<>(List.of(), "findAll desde el servicio Supplier de implementación");
    }

    @Override
    public ResponseWrapper<Supplier> findById(Long id) {
        return new ResponseWrapper<>(null, "findById desde el servicio Supplier de implementación");
    }

    @Override
    public ResponseWrapper<Supplier> create(Supplier supplier) {
        return new ResponseWrapper<>(null, "create desde el servicio Supplier de implementación");
    }

    @Override
    public ResponseWrapper<Supplier> update(Long id, Supplier supplier) {
        return new ResponseWrapper<>(null, "update desde el servicio Supplier de implementación");
    }

    @Override
    public ResponseWrapper<Supplier> delete(Long id) {
        return new ResponseWrapper<>(null, "delete desde el servicio Supplier de implementación");
    }
}
