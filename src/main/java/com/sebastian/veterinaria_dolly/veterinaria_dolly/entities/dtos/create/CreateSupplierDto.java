package com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.create;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSupplierDto {

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

}
