package com.sebastian.veterinaria_dolly.veterinaria_dolly.controllers;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Category;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.create.CreateCategoryDto;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ApiResponse;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.CustomPagedResourcesAssembler;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ErrorsValidationsResponse;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ResponseWrapper;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final PagedResourcesAssembler<Category> pagedResourcesAssembler;
    private final CustomPagedResourcesAssembler<Category> customPagedResourcesAssembler;

    @Autowired
    public CategoryController(
            CategoryService categoryService,
            PagedResourcesAssembler<Category> pagedResourcesAssembler,
            CustomPagedResourcesAssembler<Category> customPagedResourcesAssembler){
        this.categoryService = categoryService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.customPagedResourcesAssembler = customPagedResourcesAssembler;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Object>> create(@Valid @RequestBody CreateCategoryDto categoryRequest, BindingResult result){

        //? Chequeamos errores en los campos proporcionados
        if(result.hasFieldErrors()){
            ErrorsValidationsResponse errors = new ErrorsValidationsResponse();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(
                            errors.validation(result),
                            new ApiResponse.Meta(
                                    "Errores en los campos",
                                    HttpStatus.BAD_REQUEST.value(),
                                    LocalDateTime.now()
                            )
                    ));
        }

        //? Intentamos realizar el registro
        ResponseWrapper<Category> categoryNew = categoryService.create(categoryRequest);

        //? Si ocurre algún error, devolvemos null en la data y el mensaje de lo que pasó
        if( categoryNew.getData() != null ){
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(
                            categoryNew.getData(),
                            new ApiResponse.Meta(
                                    "Categoría Registrada Correctamente.",
                                    HttpStatus.CREATED.value(),
                                    LocalDateTime.now()
                            )
                    ));
        }

        //? Estamos en este punto, el registro fue correcto
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        null,
                        new ApiResponse.Meta(
                                categoryNew.getErrorMessage(),
                                HttpStatus.BAD_REQUEST.value(),
                                LocalDateTime.now()
                        )
                ));

    }

    @GetMapping("/find-all")
    public ResponseEntity<ApiResponse<PagedModel<Category>>> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(defaultValue = "") String search
    ){

        if (page < 1) page = 1; //Para controlar la página 0, y que la paginación arranque en 1.
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sort)); //Generando el esquema de paginación para aplicar y ordernamiento
        Page<Category> categories = categoryService.findAll(search, pageable); //Aplicando la paginación JPA -> Incorporo el buscador
        UriComponentsBuilder uriBuilder = ServletUriComponentsBuilder.fromCurrentRequestUri(); //Para la obtención de la URL

        PagedModel<Category> pagedModel = customPagedResourcesAssembler.toModel(categories, uriBuilder);

        return ResponseEntity.ok(new ApiResponse<>(
                pagedModel,
                new ApiResponse.Meta(
                        "Listado de categorías.",
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        ));

    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<ApiResponse<Category>> findById(@PathVariable Long id){

        ResponseWrapper<Category> category = categoryService.findById(id);
        if( category.getData() != null ){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(
                            category.getData(),
                            new ApiResponse.Meta(
                                    "Categoría obtenida por ID.",
                                    HttpStatus.OK.value(),
                                    LocalDateTime.now()
                            )
                    ));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        null,
                        new ApiResponse.Meta(
                                category.getErrorMessage(),
                                HttpStatus.BAD_REQUEST.value(),
                                LocalDateTime.now()
                        )
                ));

    }

    @PutMapping("update-by-id/{id}")
    public ResponseEntity<ApiResponse<ResponseWrapper<Category>>> update(@RequestBody Category categoryRequest, @PathVariable Long id){ //!Pendiente DTO.
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
    public ResponseEntity<ApiResponse<ResponseWrapper<Category>>> delete(@PathVariable Long id){
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
