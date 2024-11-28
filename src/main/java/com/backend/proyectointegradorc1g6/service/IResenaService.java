package com.backend.proyectointegradorc1g6.service;

import com.backend.proyectointegradorc1g6.dto.input.ResenaDtoInput;
import com.backend.proyectointegradorc1g6.dto.output.ResenaDtoOut;
import com.backend.proyectointegradorc1g6.exception.ResourceNotFoundException;

import java.util.List;

public interface IResenaService {
    ResenaDtoOut registrarResena(ResenaDtoInput resenaDtoInput);

    List<ResenaDtoOut> listarResenasPorAuto(Long autoId);

    List<ResenaDtoOut> listarResenasPorUsuario(Long usuariId);
    List<ResenaDtoOut> listarResenas();

    void eliminarResena(Long id) throws ResourceNotFoundException;
}
