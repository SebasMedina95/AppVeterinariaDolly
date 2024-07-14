package com.sebastian.veterinaria_dolly.veterinaria_dolly.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.Date;

@Data
@Entity
@Table(name = "TBL_PLAZAS")
@JsonIgnoreProperties({"services"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Plaza {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Comment("Clave primaria")
    private Long id;

    @Column(name = "NAME", nullable = false, length = 5 )
    @Comment("Nombre de la plaza")
    private String name;

    @Column(name = "AVAILABLE", nullable = false )
    @Comment("Disponibilidad de la plaza")
    private Boolean available;

    @Column(name = "STATUS" )
    @Comment("Estado de la plaza")
    private Boolean status;

    @Column(name = "USER_CREATED", nullable = true, length = 100 )
    @Comment("Usuario que creó la plaza")
    private String userCreated;

    @Column(name = "DATE_CREATED", nullable = true )
    @Comment("Fecha creación de la plaza")
    private Date dateCreated;

    @Column(name = "USER_UPDATED", nullable = true, length = 100 )
    @Comment("Usuario que actualizó la plaza")
    private String userUpdated;

    @Column(name = "DATE_UPDATED", nullable = true )
    @Comment("Fecha actualización de la plaza")
    private Date dateUpdated;

    @ManyToOne
    @JoinColumn(name = "FK_SERVICE")
    @JsonManagedReference
    @Comment("Servicio relacionado")
    private Service service;

}
