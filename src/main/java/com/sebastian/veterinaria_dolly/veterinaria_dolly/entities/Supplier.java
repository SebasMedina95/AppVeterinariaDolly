package com.sebastian.veterinaria_dolly.veterinaria_dolly.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "TBL_SUPPLIERS")
@JsonIgnoreProperties({"products"})
@Data
@EqualsAndHashCode(exclude = "products")
@ToString(exclude = "products")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Supplier {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Comment("Clave primaria")
    private Long id;

    @Column(name = "NIT", nullable = false, length = 60 )
    @Comment("Nit del proveedor")
    private String nit;

    @Column(name = "NAME", nullable = false, length = 150 )
    @Comment("Nombre del proveedor")
    private String name;

    @Column(name = "ADDRESS", nullable = false, length = 200 )
    @Comment("Dirección del proveedor")
    private String address;

    @Column(name = "EMAIL1", nullable = false, length = 150 )
    @Comment("Email 1 del proveedor")
    private String email1;

    @Column(name = "EMAIL2", nullable = true, length = 150 )
    @Comment("Email 2 del proveedor")
    private String email2;

    @Column(name = "PHONE1", nullable = false, length = 45 )
    @Comment("Teléfono 1 del proveedor")
    private String phone1;

    @Column(name = "PHONE2", nullable = true, length = 45 )
    @Comment("Teléfono 2 del proveedor")
    private String phone2;

    @Column(name = "DESCRIPTION", nullable = true, length = 1000 )
    @Comment("Descripción del proveedor")
    private String description;

    @Column(name = "STATUS" )
    @Comment("Estado del proveedor")
    private Boolean status;

    @Column(name = "USER_CREATED", nullable = true, length = 100 )
    @Comment("Usuario que creó el proveedor")
    private String userCreated;

    @Column(name = "DATE_CREATED", nullable = true )
    @Comment("Fecha creación del proveedor")
    private Date dateCreated;

    @Column(name = "USER_UPDATED", nullable = true, length = 100 )
    @Comment("Usuario que actualizó el proveedor")
    private String userUpdated;

    @Column(name = "DATE_UPDATED", nullable = true )
    @Comment("Fecha actualización del proveedor")
    private Date dateUpdated;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Product> products;

}
