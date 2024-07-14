package com.sebastian.veterinaria_dolly.veterinaria_dolly.services;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Plaza;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.create.CreatePlazaDto;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.update.UpdatePlazaDto;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ResponseWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlazaService  {

    ResponseWrapper<Plaza> create(CreatePlazaDto plaza);
    Page<Plaza> findAll(String search, Pageable pageable);
    ResponseWrapper<Plaza> findById(Long id);
    ResponseWrapper<Plaza> update(Long id, UpdatePlazaDto plaza);
    ResponseWrapper<Plaza> delete(Long id);

}
