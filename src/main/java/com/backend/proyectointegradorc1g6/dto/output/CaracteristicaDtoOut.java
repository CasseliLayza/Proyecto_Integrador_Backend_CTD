package com.backend.proyectointegradorc1g6.dto.output;

import com.backend.proyectointegradorc1g6.entity.Auto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaracteristicaDtoOut {

    private Long id;
    private String nombre;
    private String icono;


    @Override
    public String toString() {
        return "CaracteristicaDtoOut{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", icono='" + icono + '\'' +
                '}';
    }
}
