package com.backend.proyectointegradorc1g6.dto.ouput;

public class UsuarioDtoOut {

    private Long id;
    private String name;
    private String lastName;
    private int dni;
    private String email;

    public UsuarioDtoOut() {
    }

    public UsuarioDtoOut(Long id, String name, String lastName, int dni, String email) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UsuarioDtoOut{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dni='" + dni + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
