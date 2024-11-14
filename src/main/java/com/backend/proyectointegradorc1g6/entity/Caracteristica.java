package com.backend.proyectointegradorc1g6.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CARACTERISTICAS")
public class Caracteristica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String nombre;
    @Column(length = 400)
    private String icono;

    @ManyToMany(mappedBy = "caracteristicas")
    //private List<Auto> autos = new ArrayList<>();
    private List<Auto> autos;


    @Override
    public String toString() {
        return "Caracteristica{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", icono='" + icono + '\'' +
                '}';
    }
}
