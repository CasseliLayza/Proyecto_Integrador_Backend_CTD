package com.backend.proyectointegradorc1g6.dto.input;

public class UsuarioDtoInput {

    private String name;
    private String lastName;
    private String email;
    private int dni;
    private String password;

    public UsuarioDtoInput() {
    }

    public UsuarioDtoInput(String name, String lastName, String email, int dni, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.dni = dni;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UsuarioDtoInput{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", dni=" + dni +
                ", password='" + password + '\'' +
                '}';
    }
}
