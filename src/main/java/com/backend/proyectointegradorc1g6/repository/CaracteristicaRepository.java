package com.backend.proyectointegradorc1g6.repository;

import com.backend.proyectointegradorc1g6.entity.Caracteristica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CaracteristicaRepository extends JpaRepository<Caracteristica, Long> {
    Optional<Caracteristica> findByNombre(String nombre);

}
