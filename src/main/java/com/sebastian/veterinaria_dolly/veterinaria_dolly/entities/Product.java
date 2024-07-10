package com.sebastian.veterinaria_dolly.veterinaria_dolly.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "TBL_PRODUCTS")
public class Product {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(name = "NAME", unique = true, nullable = false, length = 300 )
    @Comment("Nombre del producto")
    private String name;

    @Column(name = "PRICE", nullable = false )
    @Comment("Precio del producto")
    private Float price;

    @Column(name = "STOCK", nullable = false )
    @Comment("Precio del producto")
    private Integer stock;

    @Column(name = "SLUG", unique = true, nullable = false, length = 1000 )
    @Comment("Slug del producto")
    private String slug;

    @Column(name = "DESCRIPTION", nullable = true, length = 2000 )
    @Comment("Descripción del producto")
    private String description;

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
    @JoinColumn(name = "FK_CATEGORIA")
    @JsonManagedReference
    private Category category;

    public Product() {
    }

    public Product(String name,
                   Float price,
                   Integer stock,
                   String slug,
                   String description,
                   Boolean status,
                   String userCreated,
                   Date dateCreated,
                   String userUpdated,
                   Date dateUpdated,
                   Category category) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.slug = slug;
        this.description = description;
        this.status = status;
        this.userCreated = userCreated;
        this.dateCreated = dateCreated;
        this.userUpdated = userUpdated;
        this.dateUpdated = dateUpdated;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(String userCreated) {
        this.userCreated = userCreated;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getUserUpdated() {
        return userUpdated;
    }

    public void setUserUpdated(String userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", slug='" + slug + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", userCreated='" + userCreated + '\'' +
                ", dateCreated=" + dateCreated +
                ", userUpdated='" + userUpdated + '\'' +
                ", dateUpdated=" + dateUpdated +
                ", category=" + category +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
               Objects.equals(name, product.name) &&
                Objects.equals(price, product.price) &&
                Objects.equals(stock, product.stock) &&
                Objects.equals(slug, product.slug) &&
                Objects.equals(description, product.description) &&
                Objects.equals(status, product.status) &&
                Objects.equals(userCreated, product.userCreated) &&
                Objects.equals(dateCreated, product.dateCreated) &&
                Objects.equals(userUpdated, product.userUpdated) &&
                Objects.equals(dateUpdated, product.dateUpdated) &&
                Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                name,
                price,
                stock,
                slug,
                description,
                status,
                userCreated,
                dateCreated,
                userUpdated,
                dateUpdated,
                category
        );
    }
}
