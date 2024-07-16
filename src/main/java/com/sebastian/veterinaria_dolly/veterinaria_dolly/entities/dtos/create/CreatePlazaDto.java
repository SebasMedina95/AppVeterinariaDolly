package com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.create;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePlazaDto {

    @NotEmpty(message = "El nombre de la plaza es requerido")
    @Size(min = 3, max = 5, message = "El nombre debe ser mínimo de 3 caracteres y máximo de 5")
    private String name;

    @NotNull(message = "El estado de disponibilidad es requerido")
    private Boolean available;

    @NotNull(message = "El id del servicio es requerido")
    private Long serviceId;

}
