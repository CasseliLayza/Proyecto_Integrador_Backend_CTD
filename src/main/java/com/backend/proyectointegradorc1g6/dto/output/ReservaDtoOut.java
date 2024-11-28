package com.backend.proyectointegradorc1g6.dto.output;

import com.backend.proyectointegradorc1g6.entity.Auto;
import com.backend.proyectointegradorc1g6.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDtoOut {

    private Long id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Double precioFinal;
    private String comentario;
    private String lugarEntrega;
    private String lugarRecogida;
    private boolean estado;
    private UsuarioDtoOut usuario;
    private AutoDtoOut auto;

    @Override
    public String toString() {
        return "ReservaDtoOut{" +
                "id=" + id +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", precioFinal=" + precioFinal +
                ", comentario='" + comentario + '\'' +
                ", lugarEntrega='" + lugarEntrega + '\'' +
                ", lugarRecogida='" + lugarRecogida + '\'' +
                ", estado=" + estado +
                ", usuario=" + usuario +
                ", auto=" + auto +
                '}';
    }
}


