package com.backend.proyectointegradorc1g6.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USUARIOS")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String nombre;
    @Column(length = 50)
    private String apellido;
    private int dni;
    private int edad;
    @Column(length = 50)
    private String telefono;
    @Column(length = 50, unique = true)
    private String email;

    @Column(length = 50)
    private String nacionalidad;

    //@Transient
    private boolean esAdmin;

    @Column(name = "esta_activo")
    private boolean estaActivo;
    @PrePersist
    public void prePersist(){
        estaActivo= true;
    }
    //@JsonIgnoreProperties({"usuarios","handler","hibernateLazyInitializer"})
    @ManyToMany
    @JoinTable(
            name = "USUARIO_ROL",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id", "rol_id"})}
    )
    private List<Rol> roles;
    @Column(length = 50, unique = true)
    private String userName;
    @Column(length = 100)
    private String password;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Reserva> reservas;

    @ManyToMany
    @JoinTable(
            name = "usuario_auto_favorito",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "auto_id")
    )
    private Set<Auto> autosFavoritos = new HashSet<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resena> resenas = new ArrayList<>();

    public Usuario(String nombre, String apellido, int dni, int edad, String telefono, String email, String nacionalidad, boolean esAdmin, boolean estaActivo, List<Rol> roles, String userName, String password, List<Reserva> reservas, Set<Auto> autosFavoritos, List<Resena> resenas) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.edad = edad;
        this.telefono = telefono;
        this.email = email;
        this.nacionalidad = nacionalidad;
        this.esAdmin = esAdmin;
        this.estaActivo = estaActivo;
        this.roles = roles;
        this.userName = userName;
        this.password = password;
        this.reservas = reservas;
        this.autosFavoritos = autosFavoritos;
        this.resenas = resenas;
    }

    @Override
    public String toString() {
        return "Usuario{" +
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
                ", roles=" + roles +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                //", reservas=" + reservas +
                //", autosFavoritos=" + autosFavoritos +
                '}';
    }
}
