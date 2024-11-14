package com.backend.proyectointegradorc1g6.repository;

import com.backend.proyectointegradorc1g6.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByDni(int dni);
    Usuario findByEmail(String email);
    //Usuario findByUserName(String userName);
    Optional<Usuario> findByUserName(String userName);
    //Usuario findByDniAndName(int dni, String nombre);
}
