package com.sebastian.veterinaria_dolly.veterinaria_dolly.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "TBL_PLANS")
public class Plan {

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

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Plaza> plazas;

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

    public Float getHighSeasonPrice() {
        return highSeasonPrice;
    }

    public void setHighSeasonPrice(Float highSeasonPrice) {
        this.highSeasonPrice = highSeasonPrice;
    }

    public Float getLowSeasonPrice() {
        return lowSeasonPrice;
    }

    public void setLowSeasonPrice(Float lowSeasonPrice) {
        this.lowSeasonPrice = lowSeasonPrice;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
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

    public Set<Plaza> getPlazas() {
        return plazas;
    }

    public void setPlazas(Set<Plaza> plazas) {
        this.plazas = plazas;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", highSeasonPrice=" + highSeasonPrice +
                ", lowSeasonPrice=" + lowSeasonPrice +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", images='" + images + '\'' +
                ", userCreated='" + userCreated + '\'' +
                ", dateCreated=" + dateCreated +
                ", userUpdated='" + userUpdated + '\'' +
                ", dateUpdated=" + dateUpdated +
                ", plazas=" + plazas +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plan plan = (Plan) o;
        return Objects.equals(id, plan.id) && Objects.equals(name, plan.name) && Objects.equals(highSeasonPrice, plan.highSeasonPrice) && Objects.equals(lowSeasonPrice, plan.lowSeasonPrice) && Objects.equals(status, plan.status) && Objects.equals(description, plan.description) && Objects.equals(images, plan.images) && Objects.equals(userCreated, plan.userCreated) && Objects.equals(dateCreated, plan.dateCreated) && Objects.equals(userUpdated, plan.userUpdated) && Objects.equals(dateUpdated, plan.dateUpdated) && Objects.equals(plazas, plan.plazas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, highSeasonPrice, lowSeasonPrice, status, description, images, userCreated, dateCreated, userUpdated, dateUpdated, plazas);
    }
}
