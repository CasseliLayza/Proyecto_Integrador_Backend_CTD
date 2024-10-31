package com.backend.proyectointegradorc1g6.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDtoInput {

    private String nombre;

    @Override
    public String toString() {
        return "CategoriaDtoInput{" +
                "nombre='" + nombre + '\'' +
                '}';
    }
}
