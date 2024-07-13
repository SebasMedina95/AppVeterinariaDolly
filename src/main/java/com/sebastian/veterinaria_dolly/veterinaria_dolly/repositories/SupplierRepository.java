package com.sebastian.veterinaria_dolly.veterinaria_dolly.repositories;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

//* PARA EL FILTRADOR, REQUERIMOS EXTENDER TAMBIÉN DE JpaSpecificationExecutor<Supplier>
public interface SupplierRepository extends JpaRepository<Supplier, Long>, JpaSpecificationExecutor<Supplier> {

    @Query("SELECT s FROM Supplier s WHERE s.name = :supplierName OR s.nit = :supplierNit")
    Optional<Supplier> getSupplierByNameAndByNit(String supplierName, String supplierNit);

}
