package com.backend.proyectointegradorc1g6.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailReservDto {
    private String modelo;
    private String matricula;
    private String [] salida;
    private String [] retorno;
    private String dias;
    private String precio;

    @Override
    public String toString() {
        return "EmailReservDto{" +
                "modelo='" + modelo + '\'' +
                ", matricula='" + matricula + '\'' +
                ", salida=" + Arrays.toString(salida) +
                ", retorno=" + Arrays.toString(retorno) +
                ", dias='" + dias + '\'' +
                ", precio='" + precio + '\'' +
                '}';
    }
}
