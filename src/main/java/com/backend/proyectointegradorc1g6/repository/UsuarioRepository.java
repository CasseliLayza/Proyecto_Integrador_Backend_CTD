package com.backend.proyectointegradorc1g6.repository;

import com.backend.proyectointegradorc1g6.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByDni(int dni);
    //Usuario findByDniAndName(int dni, String nombre);
}
