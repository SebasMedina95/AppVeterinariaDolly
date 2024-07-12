package com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.update;

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
public class UpdateCategoryDto {

    @NotEmpty(message = "El nombre de la categoría es requerido")
    @Size(min = 5, max = 150, message = "El nombre debe ser mínimo de 5 caracteres y máximo de 150")
    private String name;

}
