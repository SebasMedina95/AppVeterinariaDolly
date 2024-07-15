package com.sebastian.veterinaria_dolly.veterinaria_dolly.services.impl;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Category;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Plaza;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Plan;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.create.CreatePlazaDto;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.update.UpdatePlazaDto;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ResponseWrapper;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.repositories.PlazaRepository;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.repositories.PlanRepository;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.services.PlazaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PlazaServiceImpl implements PlazaService {

    static String dummiesUser = "usuario123";
    private final PlazaRepository plazaRepository;
    private final PlanRepository planRepository;

    @Autowired
    public PlazaServiceImpl(
            PlazaRepository plazaRepository,
            PlanRepository planRepository
    ){
        this.plazaRepository = plazaRepository;
        this.planRepository = planRepository;
    }

    @Override
    public ResponseWrapper<Plaza> create(CreatePlazaDto plaza){

        //? Validemos que exista el Plan a Asociar
        Optional<Plan> getPlanOptional = planRepository.findById(plaza.getServiceId());
        if( getPlanOptional.isEmpty() )
            return new ResponseWrapper<>(null, "El plan que se está intentando asociar no existe");

        Plan getPlan = getPlanOptional.orElseThrow();

        //? Validemos que no se repita la plaza
        String plazaName = plaza.getName().trim().toUpperCase();
        Optional<Plaza> getPlazaOptional = plazaRepository.getPlazaByName(plazaName);
        if( getPlazaOptional.isPresent() )
            return new ResponseWrapper<>(null, "El nombre de plaza ya se encuentra registrado");

        //? Pasamos hasta acá, registramos plaza
        Plaza newPlaza = new Plaza();
        newPlaza.setName(plazaName);
        newPlaza.setAvailable(plaza.getAvailable());
        newPlaza.setStatus(true); //* Por defecto entra en true
        newPlaza.setPlan(getPlan);
        newPlaza.setUserCreated(dummiesUser); //! Ajustar cuando se implemente Security
        newPlaza.setDateCreated(new Date()); //! Ajustar cuando se implemente Security
        newPlaza.setUserUpdated(dummiesUser); //! Ajustar cuando se implemente Security
        newPlaza.setDateUpdated(new Date()); //! Ajustar cuando se implemente Security

        return new ResponseWrapper<>(plazaRepository.save(newPlaza), "Plaza guardada correctamente");

    }

    @Override
    public Page<Plaza> findAll(String search, Pageable pageable) {

        Specification<Plaza> spec = this.searchByFilter(search);
        return plazaRepository.findAll(spec, pageable);

    }

    @Override
    public ResponseWrapper<Plaza> findById(Long id) {

        Optional<Plaza> plazaOptional = plazaRepository.findById(id);
        if( plazaOptional.isPresent() ){
            Plaza product = plazaOptional.orElseThrow();
            return new ResponseWrapper<>(product, "Plaza encontrada por ID correctamente");
        }

        return new ResponseWrapper<>(null, "La plaza no pudo ser encontrado por el ID");

    }

    @Override
    public ResponseWrapper<Plaza> update(Long id, UpdatePlazaDto plaza) {

        Optional<Plaza> plazaOptional = plazaRepository.findById(id);
        if( plazaOptional.isPresent() ){

            Plaza plazaDb = plazaOptional.orElseThrow();

            //? Validemos que el Id de Plan proporcionado existe
            Optional<Plan> getPlanOptional = planRepository.findById(plaza.getServiceId());
            if( getPlanOptional.isEmpty() )
                return new ResponseWrapper<>(null, "El plan que se está intentando asociar no existe");

            Plan getPlan = getPlanOptional.orElseThrow();

            //? Validemos que no se repita la plaza
            String plazaName = plaza.getName().trim().toUpperCase();
            Optional<Plaza> getPlazaOptional = plazaRepository.getPlazaByName(plazaName);

            if( getPlazaOptional.isPresent() )
                return new ResponseWrapper<>(null, "La categoría ya está registrada");

            //? Vamos a actualizar si llegamos hasta acá
            plazaDb.setName(plazaName);
            plazaDb.setAvailable(plaza.getAvailable());
            plazaDb.setPlan(getPlan);
            plazaDb.setUserUpdated(dummiesUser);
            plazaDb.setDateUpdated(new Date());

            return new ResponseWrapper<>(plazaRepository.save(plazaDb), "Plaza Actualizada Correctamente");

        }else{

            return new ResponseWrapper<>(null, "La Plaza no fue encontrada");

        }

    }

    @Override
    public ResponseWrapper<Plaza> delete(Long id) {

        Optional<Plaza> plazaOptional = plazaRepository.findById(id);
        if( plazaOptional.isPresent() ){

            Plaza plazaDb = plazaOptional.orElseThrow();

            //? Vamos a actualizar si llegamos hasta acá
            //? ESTO SERÁ UN ELIMINADO LÓGICO!
            plazaDb.setStatus(false);
            plazaDb.setUserUpdated("usuario123");
            plazaDb.setDateUpdated(new Date());

            return new ResponseWrapper<>(plazaRepository.save(plazaDb), "Plaza Eliminada Correctamente");

        }else{

            return new ResponseWrapper<>(null, "La Plaza no fue encontrada");

        }

    }

    //* Para el buscador de categoría.
    //? Buscarémos solo por el name (Nombre de categoría).
    //? NOTA: No olvidar el status.
    public Specification<Plaza> searchByFilter(String search) {
        return (root, query, criteriaBuilder) -> {
            if (search == null || search.isEmpty()) {
                return criteriaBuilder.isTrue(root.get("status"));
            }
            return criteriaBuilder.and(
                    criteriaBuilder.isTrue(root.get("status")),
                    criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), "%" + search.toUpperCase() + "%")
            );
        };
    }

}
