package com.backend.proyectointegradorc1g6.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResenaDtoInput {
    private Long autoId;
    private Long usuarioId;
    @Positive(message = "The value puntuacion should not null or less than zero")
    @Digits(integer = 1, fraction = 0, message = "The value puntuacion should has max 1 digits")
    private int puntuacion;
    //@NotBlank(message = "The value comentario should not be empty")
    @Size(max = 400, message = "The value comentario should has max 250 characters")
    private String comentario;

    @Override
    public String toString() {
        return "ResenaDtoInput{" +
                "autoId=" + autoId +
                ", usuarioId=" + usuarioId +
                ", puntuacion=" + puntuacion +
                ", comentario='" + comentario + '\'' +
                '}';
    }
}
