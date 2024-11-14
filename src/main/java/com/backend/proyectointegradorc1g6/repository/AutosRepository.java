package com.backend.proyectointegradorc1g6.repository;

import com.backend.proyectointegradorc1g6.entity.Auto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutosRepository extends JpaRepository<Auto, Long> {
    Auto findByMatricula(String matricula);

    boolean existsByMatricula(String matricula);

    List<Auto> findByCategorias_Id(Long categoriaId);
    List<Auto> findByCaracteristicas_Id(Long caracteristicaId);

}
