package com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.update;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UpdateSupplierDto {

    @NotEmpty(message = "El nit del proveedor es requerido")
    @Size(min = 7, max = 60, message = "El nit debe ser mínimo de 7 caracteres y máximo de 60")
    private String nit;

    @NotEmpty(message = "El nombre del proveedor es requerido")
    @Size(min = 5, max = 150, message = "El nombre debe ser mínimo de 5 caracteres y máximo de 150")
    private String name;

    @NotEmpty(message = "La dirección del proveedor es requerido")
    @Size(min = 5, max = 200, message = "La dirección debe ser mínimo de 5 caracteres y máximo de 200")
    private String address;

    @NotEmpty(message = "El email 1 del proveedor es requerido")
    @Size(min = 5, max = 200, message = "El email 1 debe ser mínimo de 5 caracteres y máximo de 150")
    @Email(message = "El email 1 debe tener un formato válido")
    private String email1;

    private String email2;

    @NotEmpty(message = "El teléfono 1 del proveedor es requerido")
    @Size(min = 10, max = 45, message = "El teléfono 1 debe ser mínimo de 10 caracteres y máximo de 45")
    private String phone1;

    private String phone2;
    private String description;

    public @NotEmpty(message = "El nit del proveedor es requerido") @Size(min = 7, max = 60, message = "El nit debe ser mínimo de 7 caracteres y máximo de 60") String getNit() {
        return nit;
    }

    public void setNit(@NotEmpty(message = "El nit del proveedor es requerido") @Size(min = 7, max = 60, message = "El nit debe ser mínimo de 7 caracteres y máximo de 60") String nit) {
        this.nit = nit;
    }

    public @NotEmpty(message = "El nombre del proveedor es requerido") @Size(min = 5, max = 150, message = "El nombre debe ser mínimo de 5 caracteres y máximo de 150") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "El nombre del proveedor es requerido") @Size(min = 5, max = 150, message = "El nombre debe ser mínimo de 5 caracteres y máximo de 150") String name) {
        this.name = name;
    }

    public @NotEmpty(message = "La dirección del proveedor es requerido") @Size(min = 5, max = 200, message = "La dirección debe ser mínimo de 5 caracteres y máximo de 200") String getAddress() {
        return address;
    }

    public void setAddress(@NotEmpty(message = "La dirección del proveedor es requerido") @Size(min = 5, max = 200, message = "La dirección debe ser mínimo de 5 caracteres y máximo de 200") String address) {
        this.address = address;
    }

    public @NotEmpty(message = "El email 1 del proveedor es requerido") @Size(min = 5, max = 200, message = "El email 1 debe ser mínimo de 5 caracteres y máximo de 150") @Email(message = "El email 1 debe tener un formato válido") String getEmail1() {
        return email1;
    }

    public void setEmail1(@NotEmpty(message = "El email 1 del proveedor es requerido") @Size(min = 5, max = 200, message = "El email 1 debe ser mínimo de 5 caracteres y máximo de 150") @Email(message = "El email 1 debe tener un formato válido") String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public @NotEmpty(message = "El teléfono 1 del proveedor es requerido") @Size(min = 10, max = 45, message = "El teléfono 1 debe ser mínimo de 10 caracteres y máximo de 45") String getPhone1() {
        return phone1;
    }

    public void setPhone1(@NotEmpty(message = "El teléfono 1 del proveedor es requerido") @Size(min = 10, max = 45, message = "El teléfono 1 debe ser mínimo de 10 caracteres y máximo de 45") String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
