package com.sebastian.veterinaria_dolly.veterinaria_dolly.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.Date;

//@Data
@Entity
@Table(name = "TBL_PLAZAS")
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
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
    @JoinColumn(name = "FK_PLAN")
    @JsonBackReference
    @Comment("Plan relacionado")
    private Plan plan;

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

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
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

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    @Override
    public String toString() {
        return "Plaza{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", available=" + available +
                ", status=" + status +
                ", userCreated='" + userCreated + '\'' +
                ", dateCreated=" + dateCreated +
                ", userUpdated='" + userUpdated + '\'' +
                ", dateUpdated=" + dateUpdated +
                ", plan=" + plan +
                '}';
    }
}
