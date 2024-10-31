package com.backend.proyectointegradorc1g6.service.imp;

import com.backend.proyectointegradorc1g6.dto.output.CategoriaDtoOut;
import com.backend.proyectointegradorc1g6.repository.CategoriaRepository;
import com.backend.proyectointegradorc1g6.service.ICategoriaService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService implements ICategoriaService {
    private final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);
    CategoriaRepository categoriaRepository;
    private ModelMapper modelMapper;

    public CategoriaService(CategoriaRepository categoriaRepository, ModelMapper modelMapper) {
        this.categoriaRepository = categoriaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CategoriaDtoOut> listarCategorias() {
        List<CategoriaDtoOut> categoriaDtoOuts = categoriaRepository.findAll().stream()
                .map(categoria -> modelMapper.map(categoria, CategoriaDtoOut.class))
                .collect(Collectors.toList());
        LOGGER.info("categoriaDtoOuts --> {}", categoriaDtoOuts);
        return categoriaDtoOuts;
    }
}
