package com.sebastian.veterinaria_dolly.veterinaria_dolly.services.impl;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Plan;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Plaza;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.create.CreatePlanDto;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.update.UpdatePlanDto;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ResponseWrapper;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.repositories.PlanRepository;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.repositories.PlazaRepository;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.services.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PlanServiceImpl implements PlanService {

    static String dummiesUser = "usuario123";
    private final PlanRepository planRepository;

    @Autowired
    public PlanServiceImpl(
            PlanRepository planRepository
    ){
        this.planRepository = planRepository;
    }

    @Override
    public ResponseWrapper<Plan> create(CreatePlanDto plan) {

        //? Validemos que no se repita la plaza
        String planName = plan.getName().trim().toUpperCase();
        Optional<Plan> getPlanOptional = planRepository.getPlanByName(planName);
        if( getPlanOptional.isPresent() )
            return new ResponseWrapper<>(null, "El nombre del plan ya se encuentra registrado");

        //? DEBEMOS HACER LO DE LA IMAGEN DEL PLAN (Revisar como hacerlo con Cloudinary)
        // *************************************** //

        //? Pasamos hasta acá, registramos plaza
        Plan newPlan = new Plan();
        newPlan.setName(planName);
        newPlan.setHighSeasonPrice(plan.getHighSeasonPrice());
        newPlan.setLowSeasonPrice(plan.getLowSeasonPrice());
        newPlan.setImages("[default]"); //* Pendiente mientras se hace la subida
        newPlan.setDescription(plan.getDescription());
        newPlan.setStatus(true); //* Por defecto entra en true
        newPlan.setUserCreated(dummiesUser); //! Ajustar cuando se implemente Security
        newPlan.setDateCreated(new Date()); //! Ajustar cuando se implemente Security
        newPlan.setUserUpdated(dummiesUser); //! Ajustar cuando se implemente Security
        newPlan.setDateUpdated(new Date()); //! Ajustar cuando se implemente Security

        return new ResponseWrapper<>(planRepository.save(newPlan), "Plan guardado correctamente");

    }

    @Override
    public Page<Plan> findAll(String search, Pageable pageable) {

        Specification<Plan> spec = this.searchByFilter(search);
        return planRepository.findAll(spec, pageable);

    }

    @Override
    public ResponseWrapper<Plan> findById(Long id) {

        Optional<Plan> planOptional = planRepository.getByIdWithPlazas(id);
        if( planOptional.isPresent() ){
            Plan plan = planOptional.orElseThrow();
            return new ResponseWrapper<>(plan, "Plan encontrado por ID correctamente");
        }

        return new ResponseWrapper<>(null, "El plan no pudo ser encontrado por el ID");

    }

    @Override
    public ResponseWrapper<Plan> update(Long id, UpdatePlanDto plan) {

        Optional<Plan> planOptional = planRepository.findById(id);
        if( planOptional.isPresent() ){

            Plan planDb = planOptional.orElseThrow();

            //? Validemos que el Id de Plan proporcionado existe
            Optional<Plan> getPlanOptional = planRepository.findById(id);
            if( getPlanOptional.isEmpty() )
                return new ResponseWrapper<>(null, "El plan no fue encontrado");

            Plan getPlan = getPlanOptional.orElseThrow();

            //? Validemos que no se repita el plan
            String planName = plan.getName().trim().toUpperCase();
            Optional<Plan> getPlanOptionalName = planRepository.getPlanByNameForEdit(planName, id);

            if( getPlanOptionalName.isPresent() )
                return new ResponseWrapper<>(null, "El nombre de plan ya está registrado");

            //? DEBEMOS HACER LO DE LA IMAGEN DEL PLAN (Revisar como hacerlo con Cloudinary)
            // *************************************** //

            //? Vamos a actualizar si llegamos hasta acá
            planDb.setName(planName);
            planDb.setHighSeasonPrice(plan.getHighSeasonPrice());
            planDb.setLowSeasonPrice(plan.getLowSeasonPrice());
            planDb.setImages("[default]"); //Mientras se hace lo de las imágenes
            planDb.setDescription(plan.getDescription());
            planDb.setUserUpdated(dummiesUser);
            planDb.setDateUpdated(new Date());

            return new ResponseWrapper<>(planRepository.save(planDb), "Plan Actualizado Correctamente");

        }else{

            return new ResponseWrapper<>(null, "El Plan no fue encontrado");

        }

    }

    @Override
    public ResponseWrapper<Plan> delete(Long id) {

        Optional<Plan> planOptional = planRepository.findById(id);
        if( planOptional.isPresent() ){

            Plan planDb = planOptional.orElseThrow();

            //? Vamos a actualizar si llegamos hasta acá
            //? ESTO SERÁ UN ELIMINADO LÓGICO!
            planDb.setStatus(false);
            planDb.setUserUpdated("usuario123");
            planDb.setDateUpdated(new Date());

            return new ResponseWrapper<>(planRepository.save(planDb), "Plan Eliminado Correctamente");

        }else{

            return new ResponseWrapper<>(null, "El Plan no fue encontrado");

        }

    }

    //* Para el buscador de planes.
    //? Buscarémos tanto por name como por description.
    //? NOTA: No olvidar el status.
    public Specification<Plan> searchByFilter(String search) {
        return (root, query, criteriaBuilder) -> {
            if (search == null || search.isEmpty()) {
                return criteriaBuilder.isTrue(root.get("status"));
            }

            String searchPatternWithUpper = "%" + search.toUpperCase() + "%"; //También podría ser toLowerCase o simplemente "%" + search + "%"

            return criteriaBuilder.and(
                    criteriaBuilder.isTrue(root.get("status")),
                    criteriaBuilder.or(
                            criteriaBuilder.like(root.get("name"), searchPatternWithUpper),
                            criteriaBuilder.like(criteriaBuilder.upper(root.get("description")), searchPatternWithUpper)
                    )
            );
        };
    }

}
