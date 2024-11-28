package com.backend.proyectointegradorc1g6.dto.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDtoInput {

    @FutureOrPresent(message = " The value fechaInicio should not be before to today")
    @NotNull(message = "Should has a value")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;
    @FutureOrPresent(message = " The value fechaFin should not be before to today")
    @NotNull(message = "Should has a value")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;
    private Double precioFinal;
    @NotBlank(message = "The value comentario should not be empty")
    @Size(max = 200, message = "The value comentario should has max 200 characters")
    private String comentario;
    @NotBlank(message = "The value lugarEntrega should not be empty")
    @Size(max = 50, message = "The value lugarEntrega should has max 50 characters")
    private String lugarEntrega;
    @NotBlank(message = "The value lugarRecogida should not be empty")
    @Size(max = 50, message = "The value lugarRecogida should has max 50 characters")
    private String lugarRecogida;
    private boolean estado;

    private UsuarioDtoInput usuario;

    private AutoDtoInput auto;

    @Override
    public String toString() {
        return "ReservaDtoInput{" +
                "fechaInicio=" + fechaInicio +
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
