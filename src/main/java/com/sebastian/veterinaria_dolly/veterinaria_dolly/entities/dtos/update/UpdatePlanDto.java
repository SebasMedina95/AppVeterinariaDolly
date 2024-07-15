package com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.update;

import jakarta.validation.constraints.*;

public class UpdatePlanDto {

    @NotEmpty(message = "El nombre del plan es requerido")
    @Size(min = 5, max = 150, message = "El nombre debe ser mínimo de 5 caracteres y máximo de 150")
    private String name;

    @NotNull(message = "El valor de temporada alta del plan es requerido")
    @Positive(message = "El valor de temporada alta del plan debe ser positivo")
    @Min(value = 1, message = "El valor de temporada alta del plan mínimo vale 1")
    private Float highSeasonPrice;

    @NotNull(message = "El valor de temporada alta del plan es requerido")
    @Positive(message = "El valor de temporada alta del plan debe ser positivo")
    @Min(value = 1, message = "El valor de temporada alta del plan mínimo vale 1")
    private Float lowSeasonPrice;

    @NotEmpty(message = "La descripción del plan es requerido")
    @Size(min = 1, max = 1000, message = "El nombre debe ser mínimo de 1 caracteres y máximo de 1000")
    private String description;

    //Por ahora lo plantearemos opcional
    private String images;

    public @NotEmpty(message = "El nombre del plan es requerido") @Size(min = 5, max = 150, message = "El nombre debe ser mínimo de 5 caracteres y máximo de 150") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "El nombre del plan es requerido") @Size(min = 5, max = 150, message = "El nombre debe ser mínimo de 5 caracteres y máximo de 150") String name) {
        this.name = name;
    }

    public @NotNull(message = "El valor de temporada alta del plan es requerido") @Positive(message = "El valor de temporada alta del plan debe ser positivo") @Min(value = 1, message = "El valor de temporada alta del plan mínimo vale 1") Float getHighSeasonPrice() {
        return highSeasonPrice;
    }

    public void setHighSeasonPrice(@NotNull(message = "El valor de temporada alta del plan es requerido") @Positive(message = "El valor de temporada alta del plan debe ser positivo") @Min(value = 1, message = "El valor de temporada alta del plan mínimo vale 1") Float highSeasonPrice) {
        this.highSeasonPrice = highSeasonPrice;
    }

    public @NotNull(message = "El valor de temporada alta del plan es requerido") @Positive(message = "El valor de temporada alta del plan debe ser positivo") @Min(value = 1, message = "El valor de temporada alta del plan mínimo vale 1") Float getLowSeasonPrice() {
        return lowSeasonPrice;
    }

    public void setLowSeasonPrice(@NotNull(message = "El valor de temporada alta del plan es requerido") @Positive(message = "El valor de temporada alta del plan debe ser positivo") @Min(value = 1, message = "El valor de temporada alta del plan mínimo vale 1") Float lowSeasonPrice) {
        this.lowSeasonPrice = lowSeasonPrice;
    }

    public @NotEmpty(message = "La descripción del plan es requerido") @Size(min = 1, max = 1000, message = "El nombre debe ser mínimo de 1 caracteres y máximo de 1000") String getDescription() {
        return description;
    }

    public void setDescription(@NotEmpty(message = "La descripción del plan es requerido") @Size(min = 1, max = 1000, message = "El nombre debe ser mínimo de 1 caracteres y máximo de 1000") String description) {
        this.description = description;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
