package com.sebastian.veterinaria_dolly.veterinaria_dolly.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.Date;

@Entity
@Table(name = "TBL_PRODUCTS")
@Data
@EqualsAndHashCode()
@ToString()
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Comment("Clave primaria")
    private Long id;

    @Column(name = "NAME", unique = true, nullable = false, length = 300 )
    @Comment("Nombre del producto")
    private String name;

    @Column(name = "PRICE", nullable = false )
    @Comment("Precio del producto")
    private Float price;

    @Column(name = "STOCK", nullable = false )
    @Comment("Stock del producto")
    private Integer stock;

    @Column(name = "SLUG", unique = true, nullable = false, length = 1000 )
    @Comment("Slug del producto")
    private String slug;

    @Column(name = "DESCRIPTION", nullable = true, length = 2000 )
    @Comment("Descripción del producto")
    private String description;

    @Column(name = "SIZE", nullable = false, length = 200 )
    @Comment("Tamaño del producto")
    private String size;

    @Column(name = "IMAGES", nullable = true, length = 5000 )
    @Comment("Galería de Imágenes")
    private String images;

    @Column(name = "STATUS" )
    @Comment("Estado del producto")
    private Boolean status;

    @Column(name = "USER_CREATED", nullable = true, length = 100 )
    @Comment("Usuario que creó el producto")
    private String userCreated;

    @Column(name = "DATE_CREATED", nullable = true )
    @Comment("Fecha creación del producto")
    private Date dateCreated;

    @Column(name = "USER_UPDATED", nullable = true, length = 100 )
    @Comment("Usuario que actualizó el producto")
    private String userUpdated;

    @Column(name = "DATE_UPDATED", nullable = true )
    @Comment("Fecha actualización del producto")
    private Date dateUpdated;

    @ManyToOne
    @JoinColumn(name = "FK_CATEGORY")
    @JsonManagedReference
    @Comment("Categoría relacionada")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "FK_SUPPLIER")
    @JsonManagedReference
    @Comment("Proveedor relacionado")
    private Supplier supplier;

}
