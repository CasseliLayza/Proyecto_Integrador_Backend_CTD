package com.backend.proyectointegradorc1g6.service.imp;

import com.backend.proyectointegradorc1g6.dto.input.UsuarioDtoInput;
import com.backend.proyectointegradorc1g6.dto.output.UsuarioDtoOut;
import com.backend.proyectointegradorc1g6.entity.Usuario;
import com.backend.proyectointegradorc1g6.exception.DniDuplicadoException;
import com.backend.proyectointegradorc1g6.exception.ResourceNotFoundException;
import com.backend.proyectointegradorc1g6.repository.UsuarioRepository;
import com.backend.proyectointegradorc1g6.service.IUsuarioService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements IUsuarioService {

    private final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

    private UsuarioRepository usuarioRepository;

    private ModelMapper modelMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public UsuarioDtoOut registrarUsuario(UsuarioDtoInput usuarioDtoInput) throws DniDuplicadoException {

        LOGGER.info("usuario input --> {}", usuarioDtoInput.toString());
        int dni = usuarioDtoInput.getDni();
        Usuario usuarioBuscado = usuarioRepository.findByDni(dni);
        if (usuarioBuscado != null) {
            throw new DniDuplicadoException("El DNI " + dni + " que intenta registrar ya existe en el sistema");
        }

        Usuario usuarioARegistrar = modelMapper.map(usuarioDtoInput, Usuario.class);
        Usuario usuarioRegistrado = usuarioRepository.save(usuarioARegistrar);
        LOGGER.info("usuarioRegistrado --> {}", usuarioRegistrado.toString());

        UsuarioDtoOut usuarioDtoOut = modelMapper.map(usuarioRegistrado, UsuarioDtoOut.class);
        LOGGER.info("usuarioDtoOut --> {}", usuarioDtoOut.toString());

        return usuarioDtoOut;


    }

    @Override
    public List<UsuarioDtoOut> listarUsuarios() {

        List<UsuarioDtoOut> usuarioDtoOuts = usuarioRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UsuarioDtoOut.class))
                .collect(Collectors.toList());
        LOGGER.info("usuarioDtoOuts --> {}", usuarioDtoOuts);

        return usuarioDtoOuts;
    }

    @Override
    public UsuarioDtoOut buscarUsuario(Long id) {
        LOGGER.info("inputId --> {}", id);

        Usuario usuarioBuscado = usuarioRepository.findById(id).orElse(null);
        LOGGER.info("usuarioBuscado --> {}", usuarioBuscado);
        UsuarioDtoOut usuarioDtoOut = null;
        if (usuarioBuscado != null) {
            usuarioDtoOut = modelMapper.map(usuarioBuscado, UsuarioDtoOut.class);
            LOGGER.info("usuarioEncontrado --> {}", usuarioDtoOut);
        } else {
            LOGGER.info("usuarioBuscado, no existe verificar --> {}", id);

        }

        return usuarioDtoOut;
    }

    @Override
    public UsuarioDtoOut actualizarUsuario(UsuarioDtoInput usuarioDtoInput, Long id) throws ResourceNotFoundException {
        LOGGER.info("usuarioDtoInput --> {}", usuarioDtoInput);
        LOGGER.info("id input --> {}", id);
        Usuario usuarioEncontrado = usuarioRepository.findById(id).orElse(null);
        UsuarioDtoOut usuarioDtoOut = null;
        if (usuarioEncontrado != null) {
            Usuario usuarioAAtualizar = modelMapper.map(usuarioDtoInput, Usuario.class);
            usuarioAAtualizar.setId(usuarioEncontrado.getId());
            Usuario usuarioActualizado = usuarioRepository.save(usuarioAAtualizar);
            LOGGER.info("usuarioActualizado --> {}", usuarioActualizado);

            usuarioDtoOut = modelMapper.map(usuarioActualizado, UsuarioDtoOut.class);
        } else {
            LOGGER.info("usuarioAActualizar, no existe verificar --> {}", id);
            //Custom exception
            throw new ResourceNotFoundException("No existe registro de usuario a actualizar con id: " + id);
        }

        return usuarioDtoOut;
    }

    @Override
    public void eliminarUsuario(Long id) throws ResourceNotFoundException {
        if (buscarUsuario(id) != null) {
            usuarioRepository.deleteById(id);
            LOGGER.info("Usuario eliminado, con id --> {}", id);
        } else {
            LOGGER.info("No existe registro de usuario con id: " + id);
            //Custom exception
            throw new ResourceNotFoundException("No existe registro de usuario con id: " + id);

        }
    }

    @Override
    public void eliminarAllUsuarios() {
        usuarioRepository.deleteAll();
    }


}
