package com.backend.proyectointegradorc1g6.service.imp;

import com.backend.proyectointegradorc1g6.dto.input.CategoriaDtoInput;
import com.backend.proyectointegradorc1g6.dto.output.CategoriaDtoOut;
import com.backend.proyectointegradorc1g6.entity.Auto;
import com.backend.proyectointegradorc1g6.entity.Categoria;
import com.backend.proyectointegradorc1g6.exception.CategoriaEnUsoException;
import com.backend.proyectointegradorc1g6.exception.CategoriaDuplicadaException;
import com.backend.proyectointegradorc1g6.exception.ResourceNotFoundException;
import com.backend.proyectointegradorc1g6.repository.CategoriaRepository;
import com.backend.proyectointegradorc1g6.service.ICategoriaService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    @Override
    public CategoriaDtoOut registrarCategoria(CategoriaDtoInput categoriaDtoInput) throws CategoriaDuplicadaException {
        LOGGER.info("categoriaDtoInput --> {}", categoriaDtoInput.toString());
        Optional<Categoria> optionalCategoria = categoriaRepository.findByNombre(categoriaDtoInput.getNombre());
        if (optionalCategoria.isPresent()) {
            throw new CategoriaDuplicadaException("La Categoria " + categoriaDtoInput.getNombre() + " que intenta registrar ya existe en el sistema");
        }

        Categoria categoriaARegistrar = modelMapper.map(categoriaDtoInput, Categoria.class);
        Categoria categoriaRegistrada = categoriaRepository.save(categoriaARegistrar);
        LOGGER.info("categoriaRegistrada --> {}", categoriaRegistrada.toString());

        CategoriaDtoOut categoriaDtoOut = modelMapper.map(categoriaRegistrada, CategoriaDtoOut.class);
        LOGGER.info("categoriaDtoOut --> {}", categoriaDtoOut.toString());

        return categoriaDtoOut;
    }

    @Override
    public CategoriaDtoOut buscarCategoria(Long id) {
        LOGGER.info("inputId --> {}", id);

        Categoria categoriaBuscada = categoriaRepository.findById(id).orElse(null);
        LOGGER.info("categoriaBuscada --> {}", categoriaBuscada);
        CategoriaDtoOut categoriaDtoOut = null;
        if (categoriaBuscada != null) {
            categoriaDtoOut = modelMapper.map(categoriaBuscada, CategoriaDtoOut.class);
            LOGGER.info("categoriaDtoOut --> {}", categoriaDtoOut);
        } else {
            LOGGER.info("categoriaBuscada, no existe verificar --> {}", id);

        }

        return categoriaDtoOut;
    }

    @Override
    public CategoriaDtoOut actualizarCategoria(CategoriaDtoInput categoriaDtoInput, Long id) throws ResourceNotFoundException, CategoriaDuplicadaException {
        LOGGER.info("categoriaDtoInput --> {}", categoriaDtoInput);
        LOGGER.info("id input --> {}", id);
        Categoria categoriaEncontrada = categoriaRepository.findById(id).orElse(null);
        CategoriaDtoOut categoriaDtoOut = null;
        if (categoriaEncontrada != null) {
            verificarDuplicidadCategoria(categoriaDtoInput, categoriaEncontrada);

            Categoria categoriaAAtualizar = modelMapper.map(categoriaDtoInput, Categoria.class);
            categoriaAAtualizar.setId(categoriaEncontrada.getId());
            Categoria categoriaActualizada = categoriaRepository.save(categoriaAAtualizar);
            LOGGER.info("categoriaActualizada --> {}", categoriaActualizada);

            categoriaDtoOut = modelMapper.map(categoriaActualizada, CategoriaDtoOut.class);
        } else {
            LOGGER.info("categoriaAActualizar, no existe verificar --> {}", id);
            //Custom exception
            throw new ResourceNotFoundException("No existe registro de categoria a actualizar con id: " + id);
        }

        return categoriaDtoOut;
    }

    private void verificarDuplicidadCategoria(CategoriaDtoInput categoriaDtoInput, Categoria categoriaEncontrada) {
        Optional<Categoria> categoriaDuplicada = categoriaRepository.findByNombre(categoriaDtoInput.getNombre());
        categoriaDuplicada.ifPresent(categoria -> {
            if (!categoria.getId().equals(categoriaEncontrada.getId())) {
                throw new RuntimeException(new CategoriaDuplicadaException(
                        "La Categoria " + categoriaDtoInput.getNombre() + " que intenta actualizar ya existe en otra categoria del sistema"
                ));
            }
        });
    }


    @Override
    public void eliminarCategoria(Long id) throws ResourceNotFoundException, CategoriaEnUsoException {
        if (buscarCategoria(id) != null) {
            List<Auto> autosConCategoria = AutoService.getAutosByCategorias_id(id);
            if (!autosConCategoria.isEmpty()) {
                Map<Long, String> autosEnUso = autosConCategoria.stream()
                        .collect(Collectors.toMap(Auto::getId, Auto::getMatricula));
                //Custom exception
                throw new CategoriaEnUsoException("No se puede eliminar la categoría, está en uso en los autos: " + autosEnUso);
            }
            categoriaRepository.deleteById(id);
            LOGGER.info("Categoria eliminada, con id --> {}", id);
        } else {
            LOGGER.info("No existe registro de categoria con id: " + id);
            //Custom exception
            throw new ResourceNotFoundException("No existe registro de categoria con id: " + id);

        }

    }
}
