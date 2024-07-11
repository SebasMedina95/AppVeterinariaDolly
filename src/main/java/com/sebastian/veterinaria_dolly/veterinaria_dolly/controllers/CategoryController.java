package com.sebastian.veterinaria_dolly.veterinaria_dolly.controllers;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Category;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ApiResponse;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> create(@RequestBody Category categoryRequest){ //! Pendiente DTO.
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(
                        categoryService.create(categoryRequest),
                        new ApiResponse.Meta(
                                "Comunicación establecida con el controlador Category (CREATE)",
                                HttpStatus.OK.value(),
                                LocalDateTime.now()
                        )
                ));
    }

    @GetMapping("/find-all")
    public ResponseEntity<ApiResponse<?>> findAll(){ //! Mirar como aplicar la paginación
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(
                        categoryService.findAll(),
                        new ApiResponse.Meta(
                                "Comunicación establecida con el controlador Category (FindAll)",
                                HttpStatus.OK.value(),
                                LocalDateTime.now()
                        )
                ));
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<ApiResponse<?>> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(
                        categoryService.findById(id),
                        new ApiResponse.Meta(
                                "Comunicación establecida con el controlador Category (findById)",
                                HttpStatus.OK.value(),
                                LocalDateTime.now()
                        )
                ));
    }

    @PutMapping("update-by-id/{id}")
    public ResponseEntity<ApiResponse<?>> update(@RequestBody Category categoryRequest, @PathVariable Long id){ //!Pendiente DTO.
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(
                        categoryService.update(id, categoryRequest),
                        new ApiResponse.Meta(
                                "Comunicación establecida con el controlador Category (update)",
                                HttpStatus.OK.value(),
                                LocalDateTime.now()
                        )
                ));
    }

    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(
                        categoryService.delete(id),
                        new ApiResponse.Meta(
                                "Comunicación establecida con el controlador Category (delete)",
                                HttpStatus.OK.value(),
                                LocalDateTime.now()
                        )
                ));
    }

}
