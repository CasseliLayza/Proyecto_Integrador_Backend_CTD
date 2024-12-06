package com.backend.proyectointegradorc1g6.dto.output;

import com.backend.proyectointegradorc1g6.dto.input.CaracteristicaDtoInput;
import com.backend.proyectointegradorc1g6.entity.Categoria;
import com.backend.proyectointegradorc1g6.entity.Resena;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AutoDtoOut {
    private Long id;
    private String matricula;
    private String descripcion;
    private String marca;
    private String modelo;
    private String potenciaHP;
    private String velocidad;
    private String aceleracion;
    private Double precioDia;
    private String fechaFabricacion;
    private boolean estaActivo;
    private List<CategoriaDtoOut> categorias;
    private List<CaracteristicaDtoOut> caracteristicas;
    private List<ImagenDtoOut> imagenes;

    private List<ResenaDtoOut> resenas;

    @Override
    public String toString() {
        return "AutoDtoOut{" +
                "id=" + id +
                ", matricula='" + matricula + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", potenciaHP='" + potenciaHP + '\'' +
                ", velocidad='" + velocidad + '\'' +
                ", aceleracion='" + aceleracion + '\'' +
                ", precioDia=" + precioDia +
                ", fechaFabricacion='" + fechaFabricacion + '\'' +
                ", estaActivo=" + estaActivo +
                ", categorias=" + categorias +
                ", caracteristicas=" + caracteristicas +
                ", imagenes=" + imagenes +
                ", resenas=" + resenas +
                '}';
    }
}
