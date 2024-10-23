package com.backend.proyectointegradorc1g6.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
//@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String name;
    @Column(length = 50)
    private String lastName;
    @Column(length = 15)
    private int dni;
    @Column(length = 50)
    private String email;
    @Column(length = 50)
    private String password;

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dni=" + dni +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
