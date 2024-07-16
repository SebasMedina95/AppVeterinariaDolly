package com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.create;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePlanDto {

    @NotEmpty(message = "El nombre del plan es requerido")
    @Size(min = 5, max = 150, message = "El nombre debe ser mínimo de 5 caracteres y máximo de 150")
    private String name;

    @NotNull(message = "El valor de temporada alta del plan es requerido")
    @Positive(message = "El valor de temporada alta del plan debe ser positivo")
    @Min(value = 1, message = "El valor de temporada alta del plan mínimo vale 1")
    private Float highSeasonPrice;

    @NotNull(message = "El valor de temporada baja del plan es requerido")
    @Positive(message = "El valor de temporada baja del plan debe ser positivo")
    @Min(value = 1, message = "El valor de temporada baja del plan mínimo vale 1")
    private Float lowSeasonPrice;

    @NotEmpty(message = "La descripción del plan es requerido")
    @Size(min = 1, max = 1000, message = "El nombre debe ser mínimo de 1 caracteres y máximo de 1000")
    private String description;

    //Por ahora lo plantearemos opcional
    private String images;

}
