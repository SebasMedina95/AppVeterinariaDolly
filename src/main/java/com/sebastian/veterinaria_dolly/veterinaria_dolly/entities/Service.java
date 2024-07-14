package com.sebastian.veterinaria_dolly.veterinaria_dolly.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "TBL_SERVICES")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Service {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Comment("Clave primaria")
    private Long id;

    @Column(name = "NAME", nullable = false, length = 150 )
    @Comment("Nombre del servicio")
    private String name;

    @Column(name = "HIGH_SEASON_PRICE", nullable = false )
    @Comment("Precio temporada alta")
    private Float highSeasonPrice;

    @Column(name = "LOW_SEASON_PRICE", nullable = false )
    @Comment("Precio temporada baja")
    private Float lowSeasonPrice;

    @Column(name = "STATUS" )
    @Comment("Estado del servicio")
    private Boolean status;

    @Column(name = "DESCRIPTION", nullable = false, length = 1000 )
    @Comment("Descripción del servicio")
    private String description;

    @Column(name = "IMAGES", nullable = false, length = 5000 )
    @Comment("Descripción del servicio")
    private String images;

    @Column(name = "USER_CREATED", nullable = true, length = 100 )
    @Comment("Usuario que creó el servicio")
    private String userCreated;

    @Column(name = "DATE_CREATED", nullable = true )
    @Comment("Fecha creación de el servicio")
    private Date dateCreated;

    @Column(name = "USER_UPDATED", nullable = true, length = 100 )
    @Comment("Usuario que actualizó el servicio")
    private String userUpdated;

    @Column(name = "DATE_UPDATED", nullable = true )
    @Comment("Fecha actualización del servicio")
    private Date dateUpdated;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Plaza> plazas;

}
