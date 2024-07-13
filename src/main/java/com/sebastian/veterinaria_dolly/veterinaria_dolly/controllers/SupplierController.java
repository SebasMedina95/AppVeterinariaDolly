package com.sebastian.veterinaria_dolly.veterinaria_dolly.controllers;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Supplier;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.create.CreateSupplierDto;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.update.UpdateSupplierDto;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ApiResponse;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.CustomPagedResourcesAssembler;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ErrorsValidationsResponse;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ResponseWrapper;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.services.SupplierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;
    private final CustomPagedResourcesAssembler<Supplier> customPagedResourcesAssembler; //Método personalizado de paginación :)

    @Autowired
    public SupplierController(
            SupplierService supplierService,
            CustomPagedResourcesAssembler<Supplier> customPagedResourcesAssembler
    ) {
        this.supplierService = supplierService;
        this.customPagedResourcesAssembler = customPagedResourcesAssembler;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Object>> create(
            @Valid
            @RequestBody CreateSupplierDto supplierRequest,
            BindingResult result
    ){

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
        ResponseWrapper<Supplier> supplierNew = supplierService.create(supplierRequest);

        //? Si no ocurre algún error, entonces registramos :)
        if( supplierNew.getData() != null ){
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(
                            supplierNew.getData(),
                            new ApiResponse.Meta(
                                    "Proveedor Registrado Correctamente.",
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
                                supplierNew.getErrorMessage(),
                                HttpStatus.BAD_REQUEST.value(),
                                LocalDateTime.now()
                        )
                ));

    }

    @GetMapping("/find-all")
    public ResponseEntity<ApiResponse<PagedModel<Supplier>>> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(defaultValue = "") String search
    ){

        if (page < 1) page = 1; //Para controlar la página 0, y que la paginación arranque en 1.
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sort)); //Generando el esquema de paginación para aplicar y ordenamiento
        Page<Supplier> suppliers = supplierService.findAll(search, pageable); //Aplicando la paginación JPA -> Incorporo el buscador
        UriComponentsBuilder uriBuilder = ServletUriComponentsBuilder.fromCurrentRequestUri(); //Para la obtención de la URL

        PagedModel<Supplier> pagedModel = customPagedResourcesAssembler.toModel(suppliers, uriBuilder);

        return ResponseEntity.ok(new ApiResponse<>(
                pagedModel,
                new ApiResponse.Meta(
                        "Listado de proveedores.",
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        ));

    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<ApiResponse<Supplier>> findById(@PathVariable Long id){

        ResponseWrapper<Supplier> supplier = supplierService.findById(id);
        if( supplier.getData() != null ){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(
                            supplier.getData(),
                            new ApiResponse.Meta(
                                    "Proveedor obtenido por ID.",
                                    HttpStatus.OK.value(),
                                    LocalDateTime.now()
                            )
                    ));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        null,
                        new ApiResponse.Meta(
                                supplier.getErrorMessage(),
                                HttpStatus.BAD_REQUEST.value(),
                                LocalDateTime.now()
                        )
                ));

    }

    @PutMapping("update-by-id/{id}")
    public ResponseEntity<ApiResponse<Object>> update(
            @Valid
            @RequestBody UpdateSupplierDto supplierRequest,
            BindingResult result,
            @PathVariable Long id
    ){

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

        ResponseWrapper<Supplier> supplierUpdate = supplierService.update(id, supplierRequest);

        if( supplierUpdate.getData() != null ){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(
                            supplierUpdate.getData(),
                            new ApiResponse.Meta(
                                    "Proveedor Actualizado Correctamente.",
                                    HttpStatus.OK.value(),
                                    LocalDateTime.now()
                            )
                    ));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        null,
                        new ApiResponse.Meta(
                                supplierUpdate.getErrorMessage(),
                                HttpStatus.BAD_REQUEST.value(),
                                LocalDateTime.now()
                        )
                ));


    }

    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<ApiResponse<Supplier>> delete(@PathVariable Long id){

        ResponseWrapper<Supplier> supplierUpdate = supplierService.delete(id);

        if( supplierUpdate.getData() != null ){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(
                            supplierUpdate.getData(),
                            new ApiResponse.Meta(
                                    "Proveedor Eliminado Correctamente.",
                                    HttpStatus.OK.value(),
                                    LocalDateTime.now()
                            )
                    ));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        null,
                        new ApiResponse.Meta(
                                supplierUpdate.getErrorMessage(),
                                HttpStatus.BAD_REQUEST.value(),
                                LocalDateTime.now()
                        )
                ));

    }

}
