package com.backend.proyectointegradorc1g6.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RolDtoOut {
    private Long id;
    private String nombre;

    @Override
    public String toString() {
        return "RolDtoOut{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
