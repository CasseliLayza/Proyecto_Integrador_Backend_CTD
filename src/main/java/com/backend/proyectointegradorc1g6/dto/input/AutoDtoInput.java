package com.backend.proyectointegradorc1g6.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AutoDtoInput {
    private String matricula;
    private String descripcion;
    private String marca;
    private String modelo;
    private String potenciaHP;
    private String velocidad;
    private String aceleracion;
    private Double precioDia;
    private LocalDate fechaFabricacion;
    private boolean estaActivo;
    private List<ImagenDtoInput> imagenes;

    @Override
    public String toString() {
        return "AutoDtoInput{" +
                "matricula='" + matricula + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", potenciaHP='" + potenciaHP + '\'' +
                ", velocidad='" + velocidad + '\'' +
                ", aceleracion='" + aceleracion + '\'' +
                ", precioDia=" + precioDia +
                ", fechaFabricacion=" + fechaFabricacion +
                ", estaActivo=" + estaActivo +
                ", imagenes=" + imagenes +
                '}';
    }
}
