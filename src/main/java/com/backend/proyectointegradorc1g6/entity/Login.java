package com.backend.proyectointegradorc1g6.entity;

import javax.persistence.*;


@Entity
@Table(name = "LOGINS")
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String email;
    @Column(length = 20)
    private String pass;

/*    public Login() {
    }

    public Login(Long id, String email, String pass) {
        this.id = id;
        this.email = email;
        this.pass = pass;
    }*/

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "Login{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}


