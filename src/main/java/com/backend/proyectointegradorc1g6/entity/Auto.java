package com.backend.proyectointegradorc1g6.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Column(length = 200)
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
    private String fechaFabricacion;
    @Column(name = "esta_activo")
    private boolean estaActivo;
    @ManyToMany
    @JoinTable(
            name = "AUTO_CATEGORIA",
            joinColumns = @JoinColumn(name = "auto_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias;

    @ManyToMany
    @JoinTable(
            name = "AUTO_CARACTERISTICA",
            joinColumns = @JoinColumn(name = "auto_id"),
            inverseJoinColumns = @JoinColumn(name = "caracteristica_id")
    )
    private List<Caracteristica> caracteristicas;
    @OneToMany(mappedBy = "auto", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JsonManagedReference
    private List<Imagen> imagenes;

    @OneToMany(mappedBy = "auto", cascade = CascadeType.ALL)
    private List<Reserva> reservas;

    @ManyToMany(mappedBy = "autosFavoritos")
    private Set<Usuario> usuariosQueLoTienenComoFavorito = new HashSet<>();

    @OneToMany(mappedBy = "auto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resena> resenas = new ArrayList<>();

    public Auto(String matricula, String descripcion, String marca, String modelo, String potenciaHP, String velocidad, String aceleracion, Double precioDia, String fechaFabricacion, boolean estaActivo, List<Categoria> categorias, List<Caracteristica> caracteristicas, List<Imagen> imagenes, List<Reserva> reservas, Set<Usuario> usuariosQueLoTienenComoFavorito, List<Resena> resenas) {
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
        this.categorias = categorias;
        this.caracteristicas = caracteristicas;
        this.imagenes = imagenes;
        this.reservas = reservas;
        this.usuariosQueLoTienenComoFavorito = usuariosQueLoTienenComoFavorito;
        this.resenas = resenas;
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
                ", categorias=" + categorias +
                ", caracteristicas=" + caracteristicas +
                ", imagenes=" + imagenes +
                ", reservas=" + reservas +
                ", usuariosQueLoTienenComoFavorito=" + usuariosQueLoTienenComoFavorito +
                //", resenas=" + resenas +
                '}';
    }
}
