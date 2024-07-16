package com.sebastian.veterinaria_dolly.veterinaria_dolly.controllers;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Plan;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.create.CreatePlanDto;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.update.UpdatePlanDto;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ApiResponse;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.CustomPagedResourcesAssembler;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ErrorsValidationsResponse;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ResponseWrapper;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.services.PlanService;
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
@RequestMapping("/api/plans")
@Tag(name = "Controlador de Planes", description = "Operaciones relacionadas con los planes")
public class PlanController {

    private final PlanService planService;
    private final CustomPagedResourcesAssembler<Plan> customPagedResourcesAssembler;

    @Autowired
    public PlanController(
            PlanService planService,
            CustomPagedResourcesAssembler<Plan> customPagedResourcesAssembler){
        this.planService = planService;
        this.customPagedResourcesAssembler = customPagedResourcesAssembler;
    }

    @PostMapping("/create")
    @Operation(summary = "Crear un plan", description = "Creación de un plan")
    public ResponseEntity<ApiResponse<Object>> create(
            @Valid
            @RequestBody CreatePlanDto planRequest,
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
        ResponseWrapper<Plan> planNew = planService.create(planRequest);

        //? Si no ocurre algún error, entonces registramos :)
        if( planNew.getData() != null ){
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(
                            planNew.getData(),
                            new ApiResponse.Meta(
                                    "Plan Registrado Correctamente.",
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
                                planNew.getErrorMessage(),
                                HttpStatus.BAD_REQUEST.value(),
                                LocalDateTime.now()
                        )
                ));

    }

    @GetMapping("/find-all")
    @Operation(summary = "Obtener todas los planes", description = "Obtener todos los planes con paginación y también aplicando filtros")
    public ResponseEntity<ApiResponse<PagedModel<Plan>>> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(defaultValue = "") String search
    ){

        if (page < 1) page = 1; //Para controlar la página 0, y que la paginación arranque en 1.
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sort)); //Generando el esquema de paginación para aplicar y ordenamiento
        Page<Plan> plans = planService.findAll(search, pageable); //Aplicando la paginación JPA -> Incorporo el buscador
        UriComponentsBuilder uriBuilder = ServletUriComponentsBuilder.fromCurrentRequestUri(); //Para la obtención de la URL

        PagedModel<Plan> pagedModel = customPagedResourcesAssembler.toModel(plans, uriBuilder);

        return ResponseEntity.ok(new ApiResponse<>(
                pagedModel,
                new ApiResponse.Meta(
                        "Listado de planes.",
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        ));

    }

    @GetMapping("/find-by-id/{id}")
    @Operation(summary = "Obtener planes por ID", description = "Obtener un plan dado el ID")
    public ResponseEntity<ApiResponse<Plan>> findById(@PathVariable Long id){

        ResponseWrapper<Plan> plan = planService.findById(id);
        if( plan.getData() != null ){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(
                            plan.getData(),
                            new ApiResponse.Meta(
                                    "Plan obtenido por ID.",
                                    HttpStatus.OK.value(),
                                    LocalDateTime.now()
                            )
                    ));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        null,
                        new ApiResponse.Meta(
                                plan.getErrorMessage(),
                                HttpStatus.BAD_REQUEST.value(),
                                LocalDateTime.now()
                        )
                ));

    }

    @PutMapping("update-by-id/{id}")
    @Operation(summary = "Actualizar un plan", description = "Actualizar un plan dado el ID")
    public ResponseEntity<ApiResponse<Object>> update(
            @Valid
            @RequestBody UpdatePlanDto planRequest,
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

        ResponseWrapper<Plan> planUpdate = planService.update(id, planRequest);

        if( planUpdate.getData() != null ){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(
                            planUpdate.getData(),
                            new ApiResponse.Meta(
                                    "Plan Actualizado Correctamente.",
                                    HttpStatus.OK.value(),
                                    LocalDateTime.now()
                            )
                    ));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        null,
                        new ApiResponse.Meta(
                                planUpdate.getErrorMessage(),
                                HttpStatus.BAD_REQUEST.value(),
                                LocalDateTime.now()
                        )
                ));

    }

    @DeleteMapping("/delete-by-id/{id}")
    @Operation(summary = "Eliminar un plan", description = "Eliminar un plan pero de manera lógica")
    public ResponseEntity<ApiResponse<Plan>> delete(@PathVariable Long id){

        ResponseWrapper<Plan> planUpdate = planService.delete(id);

        if( planUpdate.getData() != null ){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(
                            planUpdate.getData(),
                            new ApiResponse.Meta(
                                    "Plan Eliminado Correctamente.",
                                    HttpStatus.OK.value(),
                                    LocalDateTime.now()
                            )
                    ));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        null,
                        new ApiResponse.Meta(
                                planUpdate.getErrorMessage(),
                                HttpStatus.BAD_REQUEST.value(),
                                LocalDateTime.now()
                        )
                ));

    }

}
