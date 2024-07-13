package com.sebastian.veterinaria_dolly.veterinaria_dolly.controllers;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Product;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ApiResponse;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ResponseWrapper;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Controlador de Productos", description = "Operaciones relacionadas con los productos")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping("/create")
    @Operation(summary = "Crear un producto", description = "Creación de un producto")
    public ResponseEntity<ApiResponse<ResponseWrapper<Product>>> create(@RequestBody Product productRequest){ //!Pendiente DTO.
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
    @Operation(summary = "Obtener todos los productos", description = "Obtener todos los productos con paginación y también aplicando filtros")
    public ResponseEntity<ApiResponse<ResponseWrapper<List<Product>>>> findAll(){
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
    @Operation(summary = "Obtener producto por ID", description = "Obtener un producto dado el ID")
    public ResponseEntity<ApiResponse<ResponseWrapper<Product>>> findById(@PathVariable Long id){
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
    @Operation(summary = "Actualizar un producto", description = "Actualizar un producto dado el ID")
    public ResponseEntity<ApiResponse<ResponseWrapper<Product>>> update(@RequestBody Product productRequest, @PathVariable Long id){ //!Pendiente DTO.
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
    @Operation(summary = "Eliminar un producto", description = "Eliminar un producto pero de manera lógica")
    public ResponseEntity<ApiResponse<ResponseWrapper<Product>>> delete(@PathVariable Long id){
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
