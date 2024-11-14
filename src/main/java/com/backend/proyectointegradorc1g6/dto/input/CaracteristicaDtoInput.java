package com.backend.proyectointegradorc1g6.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaracteristicaDtoInput {
    @NotBlank(message = "The value nombre should not be empty")
    @Size(max = 50, message = "The value nombre should has max 50 characters")
    private String nombre;

    @NotBlank(message = "The value icono should not be empty")
    @Size(max = 400, message = "The value icono should has max 400 characters for its URL")
    private String icono;

    @Override
    public String toString() {
        return "CaracteristicaDtoInput{" +
                "nombre='" + nombre + '\'' +
                ", icono='" + icono + '\'' +
                '}';
    }
}
