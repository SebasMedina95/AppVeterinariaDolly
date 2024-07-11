package com.sebastian.veterinaria_dolly.veterinaria_dolly.controllers;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Supplier;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ApiResponse;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ResponseWrapper;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ResponseWrapper<Supplier>>> create(@RequestBody Supplier supplierRequest){ //! Pendiente DTO.
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(
                        supplierService.create(supplierRequest),
                        new ApiResponse.Meta(
                                "Comunicación establecida con el controlador Supplier (CREATE)",
                                HttpStatus.OK.value(),
                                LocalDateTime.now()
                        )
                ));
    }

    @GetMapping("/find-all")
    public ResponseEntity<ApiResponse<ResponseWrapper<List<Supplier>>>> findAll(){ //! Mirar como aplicar la paginación
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(
                        supplierService.findAll(),
                        new ApiResponse.Meta(
                                "Comunicación establecida con el controlador Supplier (FindAll)",
                                HttpStatus.OK.value(),
                                LocalDateTime.now()
                        )
                ));
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<ApiResponse<ResponseWrapper<Supplier>>> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(
                        supplierService.findById(id),
                        new ApiResponse.Meta(
                                "Comunicación establecida con el controlador Supplier (findById)",
                                HttpStatus.OK.value(),
                                LocalDateTime.now()
                        )
                ));
    }

    @PutMapping("update-by-id/{id}")
    public ResponseEntity<ApiResponse<ResponseWrapper<Supplier>>> update(@RequestBody Supplier supplierRequest, @PathVariable Long id){ //!Pendiente DTO.
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(
                        supplierService.update(id, supplierRequest),
                        new ApiResponse.Meta(
                                "Comunicación establecida con el controlador Supplier (update)",
                                HttpStatus.OK.value(),
                                LocalDateTime.now()
                        )
                ));
    }

    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<ApiResponse<ResponseWrapper<Supplier>>> delete(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(
                        supplierService.delete(id),
                        new ApiResponse.Meta(
                                "Comunicación establecida con el controlador Supplier (delete)",
                                HttpStatus.OK.value(),
                                LocalDateTime.now()
                        )
                ));
    }

}
