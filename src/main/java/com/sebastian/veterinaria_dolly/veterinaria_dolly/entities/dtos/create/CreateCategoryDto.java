package com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.create;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CreateCategoryDto {

    @NotEmpty(message = "El nombre de la categoría es requerido")
    @Size(min = 5, max = 150, message = "El nombre debe ser mínimo de 5 caracteres y máximo de 150")
    private String name;

    public @NotEmpty(message = "El nombre de la categoría es requerido") @Size(min = 5, max = 150, message = "El nombre debe ser mínimo de 5 caracteres y máximo de 150") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "El nombre de la categoría es requerido") @Size(min = 5, max = 150, message = "El nombre debe ser mínimo de 5 caracteres y máximo de 150") String name) {
        this.name = name;
    }
}
