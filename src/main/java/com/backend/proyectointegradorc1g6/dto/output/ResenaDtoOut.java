package com.backend.proyectointegradorc1g6.dto.output;

import com.backend.proyectointegradorc1g6.dto.input.ResenaDtoInput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResenaDtoOut {

    private Long id;
    private String nombreUsuario;
    private int puntuacion;
    private LocalDate fechaCreacion;
    private String comentario;
    private String usuarioId;


    @Override
    public String toString() {
        return "ResenaDtoOut{" +
                "id=" + id +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", puntuacion=" + puntuacion +
                ", fechaCreacion=" + fechaCreacion +
                ", comentario='" + comentario + '\'' +
                '}';
    }
}
