package com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.update;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductDto {

    @NotEmpty(message = "El nombre del producto es requerido")
    @Size(min = 5, max = 300, message = "El nombre debe ser mínimo de 5 caracteres y máximo de 300")
    private String name;

    @NotNull(message = "El valor del producto es requerido")
    @Positive(message = "El valor del producto debe ser positivo")
    @Min(value = 1, message = "El valor del producto debe ser mínimo de 1")
    private Float price;

    @NotNull(message = "El stock del producto es requerido")
    @Positive(message = "El stock del producto debe ser positivo")
    @Min(value = 0, message = "El stock del producto debe ser mínimo de 0")
    private Integer stock;

    @NotEmpty(message = "La descripción del producto es requerida")
    @Size(min = 1, max = 2000, message = "La descripción del producto debe ser mínimo de 1 caracteres y máximo de 2000")
    private String description;

    @NotEmpty(message = "El array de tallas del producto es requerida")
    @Size(min = 5, max = 200, message = "El array de tallas del producto debe ser mínimo de 1 caracteres y máximo de 200")
    private String size;

    //Opcional por ahora
    private String images;

}
