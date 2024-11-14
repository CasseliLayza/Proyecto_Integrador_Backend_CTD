package com.backend.proyectointegradorc1g6.dto.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDtoOut {

    private Long id;
    private String nombre;
    private String apellido;
    private int dni;
    private int edad;
    private String telefono;
    private String email;

    private String nacionalidad;
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean esAdmin;

    private boolean estaActivo;

    private List<RolDtoOut> roles;

    private String userName;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Override
    public String toString() {
        return "UsuarioDtoOut{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
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


