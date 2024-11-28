package com.backend.proyectointegradorc1g6.repository;

import com.backend.proyectointegradorc1g6.entity.Auto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AutosRepository extends JpaRepository<Auto, Long> {
    Auto findByMatricula(String matricula);

    boolean existsByMatricula(String matricula);

    List<Auto> findAllByMarca(String marca);

    List<Auto> findByCategorias_Id(Long categoriaId);

    List<Auto> findByCaracteristicas_Id(Long caracteristicaId);

    @Query("SELECT a FROM Auto a WHERE NOT EXISTS (" +
            "SELECT r FROM a.reservas r WHERE " +
            "(r.fechaInicio <= :fechaFin AND r.fechaFin >= :fechaInicio)" +
            ")")
    List<Auto> findAutosDisponibles(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

    @Query("SELECT a FROM Auto a WHERE NOT EXISTS (" +
            "SELECT r FROM a.reservas r WHERE " +
            "(r.fechaInicio <= :fechaFin AND r.fechaFin >= :fechaInicio)" +
            "AND r.id <> :reservaId" + // Excluir la reserva actual
            ")")
    List<Auto> findAutosDisponiblesExcluyendoReserva(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin, @Param("reservaId") Long reservaId);

}
