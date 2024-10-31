package com.backend.proyectointegradorc1g6.dto.input;

import com.backend.proyectointegradorc1g6.entity.Categoria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AutoDtoInput {
    @NotBlank(message = "The value matricula should not be empty")
    private String matricula;
    @NotBlank(message = "The value descripcion should not be empty")
    @Size(max = 200, message = "The value descripcion should has max 200 characters")
    private String descripcion;
    @NotBlank(message = "The value marca should not be empty")
    @Size(max = 25, message = "The value marca should has max 50 characters")
    private String marca;
    @NotBlank(message = "The value modelo should not be empty")
    @Size(max = 50, message = "The value modelo should has max 50 characters")
    private String modelo;
    @NotBlank(message = "The value potenciaHP should not be empty")
    @Size(max = 25, message = "The value potenciaHP should has max 50 characters")
    private String potenciaHP;
    @NotBlank(message = "The value velocidad should not be empty")
    @Size(max = 25, message = "The value velocidad should has max 50 characters")
    private String velocidad;
    @NotBlank(message = "The value aceleracion should not be empty")
    @Size(max = 25, message = "The value aceleracion should has max 50 characters")
    private String aceleracion;
    @NotNull(message = "Should has a value")
    private Double precioDia;
    @NotBlank(message = "The value FechaFabricacion should not be empty")
    @Size(max = 25, message = "The value FechaFabricacion should has max 50 characters")
    private String fechaFabricacion;
    private boolean estaActivo;
    @NotNull(message = "The value categorias should not be empty")
    private List<Categoria> categorias;
    @NotNull(message = "The value imagenes should not be empty")
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
                ", fechaFabricacion='" + fechaFabricacion + '\'' +
                ", estaActivo=" + estaActivo +
                ", categorias=" + categorias +
                ", imagenes=" + imagenes +
                '}';
    }
}
