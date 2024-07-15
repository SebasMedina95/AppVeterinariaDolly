package com.sebastian.veterinaria_dolly.veterinaria_dolly.services.impl;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Supplier;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.create.CreateSupplierDto;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.update.UpdateSupplierDto;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ResponseWrapper;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.repositories.SupplierRepository;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {

    static String dummiesUser = "usuario123";
    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository){
        this.supplierRepository = supplierRepository;
    }

    @Override
    public ResponseWrapper<Supplier> create(CreateSupplierDto supplier) {

        //? Validemos que no se repita el proveedor
        String supplierName = supplier.getName().trim().toUpperCase();
        String supplierNit = supplier.getNit().trim().toUpperCase(); //Evaluado como String para el dígito de verificación anexo

        Optional<Supplier> getSupplierOptional = supplierRepository.getSupplierByNameAndByNit(supplierName, supplierNit);

        if( getSupplierOptional.isPresent() )
            return new ResponseWrapper<>(null, "El proveedor ya se encuentra registrado con ese NIT y/o Nombre");

        Supplier newSupplier = new Supplier();

        newSupplier.setNit(supplierNit);
        newSupplier.setName(supplierName);
        newSupplier.setAddress(supplier.getAddress());
        newSupplier.setDescription(supplier.getDescription());
        newSupplier.setPhone1(supplier.getPhone1());
        newSupplier.setPhone2(supplier.getPhone2());
        newSupplier.setEmail1(supplier.getEmail1());
        newSupplier.setEmail2(supplier.getEmail2());
        newSupplier.setStatus(true);
        newSupplier.setUserCreated(dummiesUser); //! Ajustar cuando se implemente Security
        newSupplier.setDateCreated(new Date()); //! Ajustar cuando se implemente Security
        newSupplier.setUserUpdated(dummiesUser); //! Ajustar cuando se implemente Security
        newSupplier.setDateUpdated(new Date()); //! Ajustar cuando se implemente Security

        return new ResponseWrapper<>(supplierRepository.save(newSupplier), "Proveedor guardado correctamente");

    }

    @Override
    public Page<Supplier> findAll(String search, Pageable pageable) {

        //* La especificación me permite condensar los parámetros de búsqueda y el criterio de búsqueda
        //* de esta manera sigo usando JPA embebido para implementar el código requerido
        Specification<Supplier> spec = this.searchByFilter(search);
        return supplierRepository.findAll(spec, pageable);

    }

    @Override
    public ResponseWrapper<Supplier> findById(Long id) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(id);
        if( supplierOptional.isPresent() ){
            Supplier supplier = supplierOptional.orElseThrow();
            return new ResponseWrapper<>(supplier, "Proveedor encontrado por ID correctamente");
        }

        return new ResponseWrapper<>(null, "El proveedor no pudo ser encontrado por el ID");

    }

    @Override
    public ResponseWrapper<Supplier> update(Long id, UpdateSupplierDto supplier) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(id);
        if( supplierOptional.isPresent() ){

            Supplier supplierDb = supplierOptional.orElseThrow();

            //? Validemos que no se repita el proveedor.
            //? Si dado el caso el proveedor se repite, en este caso validemos que si es el mismo a editar, en este caso OK
            String supplierName = supplier.getName().trim().toUpperCase();
            String supplierNit = supplier.getNit().trim().toUpperCase();
            Optional<Supplier> getSupplierOptional = supplierRepository.getSupplierByNameAndByNit(supplierName, supplierNit);

            if( getSupplierOptional.isPresent() ){

                Supplier validGetSupplier = getSupplierOptional.orElseThrow();
                if( !validGetSupplier.getId().equals(id)){
                    return new ResponseWrapper<>(null, "El proveedor ya está registrado con este Nombre/Nit");
                }

            }

            supplierDb.setNit(supplierNit);
            supplierDb.setName(supplierName);
            supplierDb.setDescription(supplier.getDescription());
            supplierDb.setPhone1(supplier.getPhone1());
            supplierDb.setPhone2(supplier.getPhone2());
            supplierDb.setEmail1(supplier.getEmail1());
            supplierDb.setEmail2(supplier.getEmail2());
            supplierDb.setStatus(true);
            supplierDb.setUserUpdated(dummiesUser); //! Ajustar cuando se implemente Security
            supplierDb.setDateUpdated(new Date()); //! Ajustar cuando se implemente Security

            return new ResponseWrapper<>(supplierRepository.save(supplierDb), "Proveedor Actualizado Correctamente");

        }

        return new ResponseWrapper<>(null, "El proveedor no fue encontrado");

    }

    @Override
    public ResponseWrapper<Supplier> delete(Long id) {

        Optional<Supplier> supplierOptional = supplierRepository.findById(id);
        if( supplierOptional.isPresent() ){

            Supplier supplierDb = supplierOptional.orElseThrow();

            //? Vamos a actualizar si llegamos hasta acá
            //? ESTO SERÁ UN ELIMINADO LÓGICO!
            supplierDb.setStatus(false);
            supplierDb.setUserUpdated("usuario123");
            supplierDb.setDateUpdated(new Date());

            return new ResponseWrapper<>(supplierRepository.save(supplierDb), "Proveedor Eliminado Correctamente");

        }else{

            return new ResponseWrapper<>(null, "El proveedor no fue encontrado");

        }

    }

    //* Para el buscador de categoría.
    //? Buscarémos por nombre, nit, address, emails, phones y description.
    //? NOTA: No olvidar el status.
    public Specification<Supplier> searchByFilter(String search) {
        return (root, query, criteriaBuilder) -> {
            if (search == null || search.isEmpty()) {
                return criteriaBuilder.isTrue(root.get("status"));
            }

            String searchPatternWithUpper = "%" + search.toUpperCase() + "%"; //También podría ser toLowerCase o simplemente "%" + search + "%"

            return criteriaBuilder.and(
                    criteriaBuilder.isTrue(root.get("status")),
                    criteriaBuilder.or(
                            criteriaBuilder.like(root.get("name"), searchPatternWithUpper),
                            criteriaBuilder.like(root.get("nit"), searchPatternWithUpper),
                            criteriaBuilder.like(criteriaBuilder.upper(root.get("address")), searchPatternWithUpper),
                            criteriaBuilder.like(criteriaBuilder.upper(root.get("email1")), searchPatternWithUpper),
                            criteriaBuilder.like(criteriaBuilder.upper(root.get("email2")), searchPatternWithUpper),
                            criteriaBuilder.like(criteriaBuilder.upper(root.get("phone1")), searchPatternWithUpper),
                            criteriaBuilder.like(criteriaBuilder.upper(root.get("phone2")), searchPatternWithUpper),
                            criteriaBuilder.like(criteriaBuilder.upper(root.get("description")), searchPatternWithUpper)
                    )
            );
        };
    }

}
