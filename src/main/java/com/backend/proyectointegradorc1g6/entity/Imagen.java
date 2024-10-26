package com.backend.proyectointegradorc1g6.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "IMAGENES")
public class Imagen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private boolean esPrincipal;
    @ManyToOne
    @JoinColumn(name = "auto_id", nullable = false)
    //@JsonBackReference
    private Auto auto;


    public Imagen(String url, boolean esPrincipal, Auto auto) {
        this.url = url;
        this.esPrincipal = esPrincipal;
        this.auto = auto;
    }

    public Imagen(String url, boolean esPrincipal) {
        this.url = url;
        this.esPrincipal = esPrincipal;
    }

    @Override
    public String toString() {
        return "Imagen{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", esPrincipal=" + esPrincipal +
                ", autoId=" + auto.getId() +
                '}';
    }
}
