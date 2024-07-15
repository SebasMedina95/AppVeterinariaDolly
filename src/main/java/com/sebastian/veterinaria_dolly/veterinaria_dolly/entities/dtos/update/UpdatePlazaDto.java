package com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.update;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdatePlazaDto {

    @NotEmpty(message = "El nombre de la plaza es requerido")
    @Size(min = 3, max = 5, message = "El nombre debe ser mínimo de 3 caracteres y máximo de 5")
    private String name;

    @NotNull(message = "El estado de disponibilidad es requerido")
    private Boolean available;

    @NotNull(message = "El id del servicio es requerido")
    private Long serviceId;

    public @NotEmpty(message = "El nombre de la plaza es requerido") @Size(min = 3, max = 5, message = "El nombre debe ser mínimo de 3 caracteres y máximo de 5") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "El nombre de la plaza es requerido") @Size(min = 3, max = 5, message = "El nombre debe ser mínimo de 3 caracteres y máximo de 5") String name) {
        this.name = name;
    }

    public @NotNull(message = "El estado de disponibilidad es requerido") Boolean getAvailable() {
        return available;
    }

    public void setAvailable(@NotNull(message = "El estado de disponibilidad es requerido") Boolean available) {
        this.available = available;
    }

    public @NotNull(message = "El id del servicio es requerido") Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(@NotNull(message = "El id del servicio es requerido") Long serviceId) {
        this.serviceId = serviceId;
    }
}
