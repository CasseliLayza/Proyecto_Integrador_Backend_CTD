package com.backend.proyectointegradorc1g6.service.imp;

import com.backend.proyectointegradorc1g6.dto.input.ResenaDtoInput;
import com.backend.proyectointegradorc1g6.dto.output.ResenaDtoOut;
import com.backend.proyectointegradorc1g6.entity.Auto;
import com.backend.proyectointegradorc1g6.entity.Resena;
import com.backend.proyectointegradorc1g6.entity.Usuario;
import com.backend.proyectointegradorc1g6.exception.ResourceNotFoundException;
import com.backend.proyectointegradorc1g6.repository.ResenaRepository;
import com.backend.proyectointegradorc1g6.service.IResenaService;
import com.backend.proyectointegradorc1g6.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResenaService implements IResenaService {

    private final Logger LOGGER = LoggerFactory.getLogger(ResenaService.class);

    private ResenaRepository resenaRepository;

    private UsuarioService usuarioService;
    private ModelMapper modelMapper;

    public ResenaService(ResenaRepository resenaRepository, UsuarioService usuarioService, ModelMapper modelMapper) {
        this.resenaRepository = resenaRepository;
        this.usuarioService = usuarioService;
        this.modelMapper = modelMapper;
    }


    @Override
    public ResenaDtoOut registrarResena(ResenaDtoInput resenaDtoInput) {

        Usuario usuario = usuarioService.getUsuarioRepository().findById(resenaDtoInput.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Auto auto = AutoService.getAutosRepository().findById(resenaDtoInput.getAutoId())
                .orElseThrow(() -> new IllegalArgumentException("Auto no encontrado"));

        Resena resenaARegistrar = new Resena();
        resenaARegistrar.setUsuario(usuario);
        resenaARegistrar.setAuto(auto);
        resenaARegistrar.setPuntuacion(resenaDtoInput.getPuntuacion());
        resenaARegistrar.setComentario(resenaDtoInput.getComentario());
        resenaARegistrar.setFechaCreacion(LocalDate.now());
        resenaARegistrar.setNombreUsuario(usuario.getNombre());

        Resena resenaRegistrada = resenaRepository.save(resenaARegistrar);
        LOGGER.info("resenaRegistrada --> {}", resenaRegistrada);

        ResenaDtoOut resenaDtoOut = modelMapper.map(resenaARegistrar, ResenaDtoOut.class);
        LOGGER.info("resenaDtoOut --> {}", JsonPrinter.toString(resenaDtoOut));

        return resenaDtoOut;

    }

    @Override
    public List<ResenaDtoOut> listarResenasPorAuto(Long autoId) {

        List<ResenaDtoOut> resenaDtoOuts = resenaRepository.findByAutoId(autoId).stream()
                .map(resena -> modelMapper.map(resena, ResenaDtoOut.class))
                .collect(Collectors.toList());
        LOGGER.info("categoriaDtoOuts por auto --> {}", resenaDtoOuts);

        return resenaDtoOuts;

    }

    @Override
    public List<ResenaDtoOut> listarResenasPorUsuario(Long usuariId) {

        List<ResenaDtoOut> resenaDtoOuts = resenaRepository.findByUsuarioId(usuariId).stream()
                .map(resena -> modelMapper.map(resena, ResenaDtoOut.class))
                .collect(Collectors.toList());
        LOGGER.info("resenaDtoOuts por usuario --> {}", resenaDtoOuts);

        return resenaDtoOuts;

    }

    @Override
    public List<ResenaDtoOut> listarResenas() {

        List<ResenaDtoOut> resenaDtoOuts = resenaRepository.findAll().stream()
                .map(resena -> modelMapper.map(resena, ResenaDtoOut.class))
                .collect(Collectors.toList());
        LOGGER.info("All resenaDtoOuts --> {}", resenaDtoOuts);

        return resenaDtoOuts;

    }

    @Override
    public void eliminarResena(Long id) throws ResourceNotFoundException {
        Resena resenaBuscada = resenaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe registro de reseña con id: " + id));
            resenaRepository.deleteById(id);
            LOGGER.info("Reseña eliminada, con id --> {}", id);

        }
}
