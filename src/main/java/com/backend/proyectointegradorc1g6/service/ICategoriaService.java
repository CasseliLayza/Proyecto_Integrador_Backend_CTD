package com.backend.proyectointegradorc1g6.service;

import com.backend.proyectointegradorc1g6.dto.input.CategoriaDtoInput;
import com.backend.proyectointegradorc1g6.dto.output.CategoriaDtoOut;
import com.backend.proyectointegradorc1g6.exception.CategoriaDuplicadaException;
import com.backend.proyectointegradorc1g6.exception.CategoriaEnUsoException;
import com.backend.proyectointegradorc1g6.exception.ResourceNotFoundException;

import java.util.List;

public interface ICategoriaService {

    List<CategoriaDtoOut> listarCategorias();
    CategoriaDtoOut registrarCategoria(CategoriaDtoInput categoriaDtoInput) throws CategoriaDuplicadaException;

    CategoriaDtoOut buscarCategoria(Long id);

    CategoriaDtoOut actualizarCategoria(CategoriaDtoInput categoriaDtoInput, Long id) throws ResourceNotFoundException, CategoriaDuplicadaException;

    void eliminarCategoria(Long id) throws ResourceNotFoundException, CategoriaEnUsoException;
}
