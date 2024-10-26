package com.backend.proyectointegradorc1g6.service;

import com.backend.proyectointegradorc1g6.dto.input.AutoDtoInput;
import com.backend.proyectointegradorc1g6.dto.output.AutoDtoOut;
import com.backend.proyectointegradorc1g6.exception.MatriculaDuplicadaException;
import com.backend.proyectointegradorc1g6.exception.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IAutoService {

    AutoDtoOut registrarAuto(AutoDtoInput autoDtoInput) throws MatriculaDuplicadaException;

    List<AutoDtoOut> listarAutos();

    AutoDtoOut buscarAuto(Long id);

    AutoDtoOut actualizarAuto(AutoDtoInput autoDtoInput, Long id) throws ResourceNotFoundException;

    void eliminarAuto(Long id) throws ResourceNotFoundException;

    AutoDtoOut registrarAuto(AutoDtoInput autoDtoInput, List<MultipartFile> imagenes, int indiceImagenPrincipal) throws MatriculaDuplicadaException;
}
