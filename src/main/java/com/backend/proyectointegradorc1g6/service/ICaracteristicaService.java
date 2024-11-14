package com.backend.proyectointegradorc1g6.service;

import com.backend.proyectointegradorc1g6.dto.input.CaracteristicaDtoInput;
import com.backend.proyectointegradorc1g6.dto.output.CaracteristicaDtoOut;
import com.backend.proyectointegradorc1g6.exception.CaracteristicaDuplicadaException;
import com.backend.proyectointegradorc1g6.exception.CaracteristicaEnUsoException;
import com.backend.proyectointegradorc1g6.exception.ResourceNotFoundException;

import java.util.List;

public interface ICaracteristicaService {
    List<CaracteristicaDtoOut> listarCaracteristicas();

    CaracteristicaDtoOut registrarCaracteristica(CaracteristicaDtoInput caracteristicaDtoInput) throws CaracteristicaDuplicadaException;

    CaracteristicaDtoOut buscarCaracteristica(Long id);

    CaracteristicaDtoOut actualizarCaracteristica(CaracteristicaDtoInput caracteristicaDtoInput, Long id) throws ResourceNotFoundException, CaracteristicaDuplicadaException;

    void eliminarCaracteristica(Long id) throws ResourceNotFoundException, CaracteristicaEnUsoException;
}
