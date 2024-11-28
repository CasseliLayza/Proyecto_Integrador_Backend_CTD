package com.backend.proyectointegradorc1g6.service;

import com.backend.proyectointegradorc1g6.dto.input.ReservaDtoInput;
import com.backend.proyectointegradorc1g6.dto.input.ReservaRsmDtoInput;
import com.backend.proyectointegradorc1g6.dto.output.ReservaDtoOut;
import com.backend.proyectointegradorc1g6.exception.BadRequestException;
import com.backend.proyectointegradorc1g6.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface IReservaService {

    ReservaDtoOut registrarReserva(ReservaDtoInput reservaDtoInput) throws BadRequestException;
    ReservaDtoOut crearReserva(ReservaRsmDtoInput reservaRsmDtoInput );

    List<ReservaDtoOut> listarReservas();

    ReservaDtoOut buscarReserva(Long id);

    ReservaDtoOut actualizarReserva(ReservaRsmDtoInput reservaDtoInput, Long id) throws ResourceNotFoundException;

    void eliminarReserva(Long id) throws ResourceNotFoundException;
}
