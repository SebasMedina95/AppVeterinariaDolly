package com.sebastian.veterinaria_dolly.veterinaria_dolly.controllers;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Product;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.create.CreateProductDto;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.uploads.ValidImageType;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ApiResponse;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ErrorsValidationsResponse;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ResponseWrapper;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Controlador de Productos", description = "Operaciones relacionadas con los productos")
public class ProductController {

    private final ProductService productService;
    private final ValidImageType validImageType;

    @Autowired
    public ProductController(
            ProductService productService,
            ValidImageType validImageType
    ){
        this.productService = productService;
        this.validImageType = validImageType;
    }

    @PostMapping(value = "/create", consumes = "multipart/form-data")
    @Operation(summary = "Crear un producto", description = "Creación de un producto")
    public ResponseEntity<ApiResponse<Object>> create(
            @Valid @ModelAttribute CreateProductDto productCreateDTO,
            BindingResult result
            //@RequestParam(value = "images", required = false) List<MultipartFile> images //Para manejar mejor el tema de las imágenes
    ) throws IOException {

        // Validar tipo de archivo
        if(!productCreateDTO.getImages().isEmpty()){
            for (MultipartFile file : productCreateDTO.getImages()) {
                if (!validImageType.isValidImageType(file)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ApiResponse<>(
                                    null,
                                    new ApiResponse.Meta(
                                            "Está intentando adjuntar imágenes y solo se permiten en formato JPG, JPEG y PNG",
                                            HttpStatus.BAD_REQUEST.value(),
                                            LocalDateTime.now()
                                    )
                            ));
                }
            }
        }

        CreateProductDto productRequest = new CreateProductDto();
        productRequest.setName(productCreateDTO.getName());
        productRequest.setPrice(productCreateDTO.getPrice());
        productRequest.setStock(productCreateDTO.getStock());
        productRequest.setDescription(productCreateDTO.getDescription());
        productRequest.setCategoryId(productCreateDTO.getCategoryId());
        productRequest.setSupplierId(productCreateDTO.getSupplierId());
        productRequest.setSizes(String.join(",", productCreateDTO.getSizes()));
        productRequest.setImages(productCreateDTO.getImages());

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
        ResponseWrapper<Product> productNew = productService.create(productRequest);

        //? Si no ocurre algún error, entonces registramos :)
        if( productNew.getData() != null ){
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(
                            productNew.getData(),
                            new ApiResponse.Meta(
                                    "Producto Registrado Correctamente.",
                                    HttpStatus.CREATED.value(),
                                    LocalDateTime.now()
                            )
                    ));
        }

        //? Estamos en este punto, el registro no fue correcto
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        null,
                        new ApiResponse.Meta(
                                productNew.getErrorMessage(),
                                HttpStatus.BAD_REQUEST.value(),
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
