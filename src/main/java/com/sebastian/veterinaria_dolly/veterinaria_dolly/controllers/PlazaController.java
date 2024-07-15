package com.sebastian.veterinaria_dolly.veterinaria_dolly.controllers;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Plaza;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.create.CreatePlazaDto;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.update.UpdatePlazaDto;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ApiResponse;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.CustomPagedResourcesAssembler;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ErrorsValidationsResponse;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ResponseWrapper;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.services.PlazaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/plazas")
@Tag(name = "Controlador de Plazas", description = "Operaciones relacionadas con las plazas")
public class PlazaController {

    private final PlazaService plazaService;
    private final CustomPagedResourcesAssembler<Plaza> customPagedResourcesAssembler;

    @Autowired
    public PlazaController(
            PlazaService plazaService,
            CustomPagedResourcesAssembler<Plaza> customPagedResourcesAssembler){
        this.plazaService = plazaService;
        this.customPagedResourcesAssembler = customPagedResourcesAssembler;
    }

    @PostMapping("/create")
    @Operation(summary = "Crear una plaza", description = "Creación de una plaza")
    public ResponseEntity<ApiResponse<Object>> create(
            @Valid
            @RequestBody CreatePlazaDto plazaRequest,
            BindingResult result
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

        //? Intentamos realizar el registro
        ResponseWrapper<Plaza> plazaNew = plazaService.create(plazaRequest);

        //? Si no ocurre algún error, entonces registramos :)
        if( plazaNew.getData() != null ){
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(
                            plazaNew.getData(),
                            new ApiResponse.Meta(
                                    "Plaza Registrada Correctamente.",
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
                                plazaNew.getErrorMessage(),
                                HttpStatus.BAD_REQUEST.value(),
                                LocalDateTime.now()
                        )
                ));

    }

    @GetMapping("/find-all")
    @Operation(summary = "Obtener todas las plazas", description = "Obtener todas las plazas con paginación y también aplicando filtros")
    public ResponseEntity<ApiResponse<PagedModel<Plaza>>> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(defaultValue = "") String search
    ){

        if (page < 1) page = 1; //Para controlar la página 0, y que la paginación arranque en 1.
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sort)); //Generando el esquema de paginación para aplicar y ordenamiento
        Page<Plaza> plazas = plazaService.findAll(search, pageable); //Aplicando la paginación JPA -> Incorporo el buscador
        UriComponentsBuilder uriBuilder = ServletUriComponentsBuilder.fromCurrentRequestUri(); //Para la obtención de la URL

        PagedModel<Plaza> pagedModel = customPagedResourcesAssembler.toModel(plazas, uriBuilder);

        return ResponseEntity.ok(new ApiResponse<>(
                pagedModel,
                new ApiResponse.Meta(
                        "Listado de plazas.",
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        ));

    }

    @GetMapping("/find-by-id/{id}")
    @Operation(summary = "Obtener plaza por ID", description = "Obtener una plaza dado el ID")
    public ResponseEntity<ApiResponse<Plaza>> findById(@PathVariable Long id){

        ResponseWrapper<Plaza> plaza = plazaService.findById(id);
        if( plaza.getData() != null ){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(
                            plaza.getData(),
                            new ApiResponse.Meta(
                                    "Plaza obtenida por ID.",
                                    HttpStatus.OK.value(),
                                    LocalDateTime.now()
                            )
                    ));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        null,
                        new ApiResponse.Meta(
                                plaza.getErrorMessage(),
                                HttpStatus.BAD_REQUEST.value(),
                                LocalDateTime.now()
                        )
                ));

    }

    @PutMapping("update-by-id/{id}")
    @Operation(summary = "Actualizar una plaza", description = "Actualizar una plaza dado el ID")
    public ResponseEntity<ApiResponse<Object>> update(
            @Valid
            @RequestBody UpdatePlazaDto plazaRequest,
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

        ResponseWrapper<Plaza> plazaUpdate = plazaService.update(id, plazaRequest);

        if( plazaUpdate.getData() != null ){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(
                            plazaUpdate.getData(),
                            new ApiResponse.Meta(
                                    "Plaza Actualizada Correctamente.",
                                    HttpStatus.OK.value(),
                                    LocalDateTime.now()
                            )
                    ));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        null,
                        new ApiResponse.Meta(
                                plazaUpdate.getErrorMessage(),
                                HttpStatus.BAD_REQUEST.value(),
                                LocalDateTime.now()
                        )
                ));

    }

    @DeleteMapping("/delete-by-id/{id}")
    @Operation(summary = "Eliminar una plaza", description = "Eliminar una plaza pero de manera lógica")
    public ResponseEntity<ApiResponse<Plaza>> delete(@PathVariable Long id){

        ResponseWrapper<Plaza> plazaUpdate = plazaService.delete(id);

        if( plazaUpdate.getData() != null ){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(
                            plazaUpdate.getData(),
                            new ApiResponse.Meta(
                                    "Plaza Eliminada Correctamente.",
                                    HttpStatus.OK.value(),
                                    LocalDateTime.now()
                            )
                    ));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        null,
                        new ApiResponse.Meta(
                                plazaUpdate.getErrorMessage(),
                                HttpStatus.BAD_REQUEST.value(),
                                LocalDateTime.now()
                        )
                ));

    }

}
