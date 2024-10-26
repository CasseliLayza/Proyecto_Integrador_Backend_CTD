package com.backend.proyectointegradorc1g6.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "AUTOS")
public class Auto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String matricula;
    @Column(length = 50)
    private String descripcion;
    @Column(length = 50)
    private String marca;
    @Column(length = 50)
    private String modelo;
    @Column(length = 50)
    private String potenciaHP;
    @Column(length = 20)
    private String velocidad;
    @Column(length = 20)
    private String aceleracion;
    @Column(name = "precio_dia")
    private Double precioDia;
    @Column(name = "fecha_fabricacion")
    private LocalDate fechaFabricacion;
    @Column(name = "esta_activo")
    private boolean estaActivo;
    @OneToMany(mappedBy = "auto", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JsonManagedReference
    private List<Imagen> imagenes;

    public Auto(String matricula, String descripcion, String marca, String modelo, String potenciaHP, String velocidad, String aceleracion, Double precioDia, LocalDate fechaFabricacion, boolean estaActivo, List<Imagen> imagenes) {
        this.matricula = matricula;
        this.descripcion = descripcion;
        this.marca = marca;
        this.modelo = modelo;
        this.potenciaHP = potenciaHP;
        this.velocidad = velocidad;
        this.aceleracion = aceleracion;
        this.precioDia = precioDia;
        this.fechaFabricacion = fechaFabricacion;
        this.estaActivo = estaActivo;
        this.imagenes = imagenes;
    }

    @Override
    public String toString() {
        return "Auto{" +
                "id=" + id +
                ", matricula='" + matricula + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", potenciaHP='" + potenciaHP + '\'' +
                ", velocidad='" + velocidad + '\'' +
                ", aceleracion='" + aceleracion + '\'' +
                ", precioDia=" + precioDia +
                ", fechaFabricacion='" + fechaFabricacion + '\'' +
                ", estaActivo=" + estaActivo +
                ", imagenes=" + imagenes +
                '}';
    }
}
