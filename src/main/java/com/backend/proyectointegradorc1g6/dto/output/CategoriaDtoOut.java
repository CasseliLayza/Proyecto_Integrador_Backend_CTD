package com.backend.proyectointegradorc1g6.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDtoOut {
    private Long id;
    private String nombre;

    @Override
    public String toString() {
        return "CategoriaDtoOut{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
