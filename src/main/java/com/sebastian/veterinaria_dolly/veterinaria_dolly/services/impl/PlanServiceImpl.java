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
    public ResponseWrapper<Plan> update(Long id, UpdatePlanDto service) {
        return null;
    }

    @Override
    public ResponseWrapper<Plan> delete(Long id) {
        return null;
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
