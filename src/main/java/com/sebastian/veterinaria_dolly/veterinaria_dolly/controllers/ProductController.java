package com.sebastian.veterinaria_dolly.veterinaria_dolly.controllers;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Product;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ApiResponse;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ErrorsValidationsResponse;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> create(@RequestBody Product productRequest){ //!Pendiente DTO.
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(
                        productService.create(productRequest),
                        new ApiResponse.Meta(
                                "Comunicación establecida con el controlador Product (CREATE)",
                                HttpStatus.OK.value(),
                                LocalDateTime.now()
                        )
                ));
    }

    @GetMapping("/find-all")
    public ResponseEntity<ApiResponse<?>> findAll(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(
                        productService.findAll(),
                        new ApiResponse.Meta(
                                "Comunicación establecida con el controlador Product (FindAll)",
                                HttpStatus.OK.value(),
                                LocalDateTime.now()
                        )
                ));
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<ApiResponse<?>> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(
                        productService.findById(id),
                        new ApiResponse.Meta(
                                "Comunicación establecida con el controlador Product (findById)",
                                HttpStatus.OK.value(),
                                LocalDateTime.now()
                        )
                ));
    }

    @PutMapping("update-by-id/{id}")
    public ResponseEntity<ApiResponse<?>> update(@RequestBody Product productRequest, @PathVariable Long id){ //!Pendiente DTO.
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(
                        productService.update(id, productRequest),
                        new ApiResponse.Meta(
                                "Comunicación establecida con el controlador Product (update)",
                                HttpStatus.OK.value(),
                                LocalDateTime.now()
                        )
                ));
    }

    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(
                        productService.delete(id),
                        new ApiResponse.Meta(
                                "Comunicación establecida con el controlador Product (delete)",
                                HttpStatus.OK.value(),
                                LocalDateTime.now()
                        )
                ));
    }

}
