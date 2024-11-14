package com.backend.proyectointegradorc1g6.service.imp;

import com.backend.proyectointegradorc1g6.dto.input.CaracteristicaDtoInput;
import com.backend.proyectointegradorc1g6.dto.output.CaracteristicaDtoOut;
import com.backend.proyectointegradorc1g6.entity.Auto;
import com.backend.proyectointegradorc1g6.entity.Caracteristica;
import com.backend.proyectointegradorc1g6.exception.CaracteristicaDuplicadaException;
import com.backend.proyectointegradorc1g6.exception.CaracteristicaEnUsoException;
import com.backend.proyectointegradorc1g6.exception.ResourceNotFoundException;
import com.backend.proyectointegradorc1g6.repository.CaracteristicaRepository;
import com.backend.proyectointegradorc1g6.service.ICaracteristicaService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CaracteristicaService implements ICaracteristicaService {
    private final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);
    private CaracteristicaRepository caracteristicaRepository;
    private ModelMapper modelMapper;

    public CaracteristicaService(CaracteristicaRepository caracteristicaRepository, ModelMapper modelMapper) {
        this.caracteristicaRepository = caracteristicaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CaracteristicaDtoOut> listarCaracteristicas() {
        List<CaracteristicaDtoOut> caracteristicaDtoOuts = caracteristicaRepository.findAll().stream()
                .map(caracteristica -> modelMapper.map(caracteristica, CaracteristicaDtoOut.class))
                .collect(Collectors.toList());
        LOGGER.info("caracteristicaDtoOuts --> {}", caracteristicaDtoOuts);
        return caracteristicaDtoOuts;
    }

    @Override
    public CaracteristicaDtoOut registrarCaracteristica(CaracteristicaDtoInput caracteristicaDtoInput) throws CaracteristicaDuplicadaException {
        LOGGER.info("caracteristicaDtoInput --> {}", caracteristicaDtoInput.toString());
        Optional<Caracteristica> optionalCaracteristica = caracteristicaRepository.findByNombre(caracteristicaDtoInput.getNombre());
        if (optionalCaracteristica.isPresent()) {
            throw new CaracteristicaDuplicadaException("La Caracteristica " + caracteristicaDtoInput.getNombre() + " que intenta registrar ya existe en el sistema");
        }

        Caracteristica caracteristicaARegistrar = modelMapper.map(caracteristicaDtoInput, Caracteristica.class);
        Caracteristica caracteristicaRegistrada = caracteristicaRepository.save(caracteristicaARegistrar);
        LOGGER.info("caracteristicaRegistrada --> {}", caracteristicaRegistrada.toString());

        CaracteristicaDtoOut caracteristicaDtoOut = modelMapper.map(caracteristicaRegistrada, CaracteristicaDtoOut.class);
        LOGGER.info("caracteristicaDtoOut --> {}", caracteristicaDtoOut.toString());

        return caracteristicaDtoOut;
    }

    @Override
    public CaracteristicaDtoOut buscarCaracteristica(Long id) {
        LOGGER.info("inputId --> {}", id);

        Caracteristica caracteristicaBuscada = caracteristicaRepository.findById(id).orElse(null);
        LOGGER.info("caracteristicaBuscada --> {}", caracteristicaBuscada);
        CaracteristicaDtoOut caracteristicaDtoOut = null;
        if (caracteristicaBuscada != null) {
            caracteristicaDtoOut = modelMapper.map(caracteristicaBuscada, CaracteristicaDtoOut.class);
            LOGGER.info("caracteristicaDtoOut --> {}", caracteristicaDtoOut);
        } else {
            LOGGER.info("caracteristicaBuscada, no existe verificar --> {}", id);

        }

        return caracteristicaDtoOut;
    }

    @Override
    public CaracteristicaDtoOut actualizarCaracteristica(CaracteristicaDtoInput caracteristicaDtoInput, Long id) throws ResourceNotFoundException, CaracteristicaDuplicadaException {
        LOGGER.info("caracteristicaDtoInput --> {}", caracteristicaDtoInput);
        LOGGER.info("id input --> {}", id);
        Caracteristica caracteristicaEncontrada = caracteristicaRepository.findById(id).orElse(null);
        CaracteristicaDtoOut caracteristicaDtoOut = null;
        if (caracteristicaEncontrada != null) {
            verificarDuplicidadCaracteristica(caracteristicaDtoInput, caracteristicaEncontrada);

            Caracteristica caracteristicaAAtualizar = modelMapper.map(caracteristicaDtoInput, Caracteristica.class);
            caracteristicaAAtualizar.setId(caracteristicaEncontrada.getId());
            Caracteristica caracteristicaActualizada = caracteristicaRepository.save(caracteristicaAAtualizar);
            LOGGER.info("caracteristicaActualizada --> {}", caracteristicaActualizada);

            caracteristicaDtoOut = modelMapper.map(caracteristicaActualizada, CaracteristicaDtoOut.class);
        } else {
            LOGGER.info("caracteristicaAActualizar, no existe verificar --> {}", id);
            //Custom exception
            throw new ResourceNotFoundException("No existe registro de caracteristica a actualizar con id: " + id);
        }

        return caracteristicaDtoOut;
    }

    private void verificarDuplicidadCaracteristica(CaracteristicaDtoInput caracteristicaDtoInput, Caracteristica caracteristicaEncontrada) {
        Optional<Caracteristica> caracteristicasDuplicadas = caracteristicaRepository.findByNombre(caracteristicaDtoInput.getNombre());
        caracteristicasDuplicadas.ifPresent(caracteristica -> {
            if (!caracteristica.getId().equals(caracteristicaEncontrada.getId())) {
                throw new RuntimeException(new CaracteristicaDuplicadaException(
                        "La Caracteristica " + caracteristicaDtoInput.getNombre() + " que intenta actualizar ya existe en otra caracteristica del sistema"
                ));
            }
        });
    }

    @Override
    public void eliminarCaracteristica(Long id) throws ResourceNotFoundException, CaracteristicaEnUsoException {
        if (buscarCaracteristica(id) != null) {
            List<Auto> autosConCaracteristica = AutoService.getAutosByCaracteristica_id(id);
            if (!autosConCaracteristica.isEmpty()) {
                Map<Long, String> autosEnUso = autosConCaracteristica.stream()
                        .collect(Collectors.toMap(Auto::getId, Auto::getMatricula));
                //Custom exception
                throw new CaracteristicaEnUsoException("No se puede eliminar la Caracteristica, está en uso en los autos: " + autosEnUso);
            }
            caracteristicaRepository.deleteById(id);
            LOGGER.info("Caracteristica eliminada, con id --> {}", id);
        } else {
            LOGGER.info("No existe registro de Caracteristica con id: " + id);
            //Custom exception
            throw new ResourceNotFoundException("No existe registro de Caracteristica con id: " + id);

        }
    }


}
