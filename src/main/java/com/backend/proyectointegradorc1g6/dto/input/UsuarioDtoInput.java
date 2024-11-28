package com.backend.proyectointegradorc1g6.dto.input;

import com.backend.proyectointegradorc1g6.dto.input.dtoUtils.OnCreate;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UsuarioDtoInput {

    @NotBlank(message = "The value nombre should not be empty")
    @Size(max = 50, message = "The value nombre should has max 50 characters")
    private String nombre;
    @NotBlank(message = "The value apellido should not be empty")
    @Size(max = 50, message = "The value apellido should has max 50 characters")
    private String apellido;
    private int dni;
    private int edad;
    private String telefono;
    @NotBlank(message = "The value email should not be empty")
    @Size(max = 50, message = "The value email should has max 50 characters")
    private String email;

    @NotBlank(message = "The value nacionalidad should not be empty")
    @Size(max = 50, message = "The value nacionalidad should has max 50 characters")
    private String nacionalidad;

    private boolean esAdmin;

    private boolean estaActivo;
    @NotBlank(message = "The value userName should not be empty")
    @Size(max = 55, message = "The value userName should has max 55 characters")
    private String userName;
    @NotBlank(message = "The value password should not be empty", groups = OnCreate.class)
    @Size(max = 100, message = "The value password should has max 100 characters")
    private String password;

    @Override
    public String toString() {
        return "UsuarioDtoInput{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni=" + dni +
                ", edad=" + edad +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", esAdmin=" + esAdmin +
                ", estaActivo=" + estaActivo +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

