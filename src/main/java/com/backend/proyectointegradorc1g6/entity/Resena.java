package com.backend.proyectointegradorc1g6.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RESENAS")
public class Resena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "auto_id", nullable = false)
    private Auto auto;

    @Column(nullable = false)
    private int puntuacion;

    @Column(nullable = false)
    private LocalDate fechaCreacion;

    @Column(length = 250)
    private String comentario;

    @Column(nullable = false)
    private String nombreUsuario;

    public Resena(Usuario usuario, Auto auto, int puntuacion, LocalDate fechaCreacion, String comentario, String nombreUsuario) {
        this.usuario = usuario;
        this.auto = auto;
        this.puntuacion = puntuacion;
        this.fechaCreacion = fechaCreacion;
        this.comentario = comentario;
        this.nombreUsuario = nombreUsuario;
    }

    @Override
    public String toString() {
        return "Resena{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", auto=" + auto +
                ", puntuacion=" + puntuacion +
                ", fechaCreacion=" + fechaCreacion +
                ", comentario='" + comentario + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                '}';
    }
}
