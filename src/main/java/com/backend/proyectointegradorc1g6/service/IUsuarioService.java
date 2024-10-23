package com.backend.proyectointegradorc1g6.service;

import com.backend.proyectointegradorc1g6.dto.input.UsuarioDtoInput;
import com.backend.proyectointegradorc1g6.dto.ouput.UsuarioDtoOut;
import com.backend.proyectointegradorc1g6.exception.DniDuplicadoException;
import com.backend.proyectointegradorc1g6.exception.ResourceNotFoundException;

import java.util.List;

public interface IUsuarioService {
    UsuarioDtoOut registrarUsuario(UsuarioDtoInput usuarioDtoInput) throws DniDuplicadoException;

    List<UsuarioDtoOut> listarUsuarios();

    UsuarioDtoOut buscarUsuario(Long id);

    UsuarioDtoOut actualizarUsuario(UsuarioDtoInput usuarioDtoInput, Long id) throws ResourceNotFoundException;

    void eliminarUsuario(Long id) throws ResourceNotFoundException;

    void eliminarAllUsuarios();
}
