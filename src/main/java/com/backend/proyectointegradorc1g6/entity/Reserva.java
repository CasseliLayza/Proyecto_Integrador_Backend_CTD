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
@Table(name = "RESERVAS")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Double precioFinal;
    @Column(length = 200)
    private String comentario;
    @Column(length = 50)
    private String lugarEntrega;
    @Column(length = 50)
    private String lugarRecogida;
    private boolean estado;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "auto_id")
    private Auto auto;

    public Reserva(LocalDate fechaInicio, LocalDate fechaFin, Double precioFinal, String comentario, String lugarEntrega, String lugarRecogida, boolean estado, Usuario usuario, Auto auto) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precioFinal = precioFinal;
        this.comentario = comentario;
        this.lugarEntrega = lugarEntrega;
        this.lugarRecogida = lugarRecogida;
        this.estado = estado;
        this.usuario = usuario;
        this.auto = auto;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", precioFinal=" + precioFinal +
                ", comentario='" + comentario + '\'' +
                ", lugarEntrega='" + lugarEntrega + '\'' +
                ", lugarRecogida='" + lugarRecogida + '\'' +
                ", estado=" + estado +
                ", usuario=" + usuario +
                ", auto=" + auto.getId() +
                '}';
    }
}
