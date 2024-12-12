package com.backend.proyectointegradorc1g6.service.imp;

import com.backend.proyectointegradorc1g6.dto.input.UsuarioDtoInput;
import com.backend.proyectointegradorc1g6.dto.output.UsuarioDtoOut;
import com.backend.proyectointegradorc1g6.entity.Auto;
import com.backend.proyectointegradorc1g6.entity.Rol;
import com.backend.proyectointegradorc1g6.entity.Usuario;
import com.backend.proyectointegradorc1g6.exception.DniDuplicadoException;
import com.backend.proyectointegradorc1g6.exception.ResourceNotFoundException;
import com.backend.proyectointegradorc1g6.repository.RolRepository;
import com.backend.proyectointegradorc1g6.repository.UsuarioRepository;
import com.backend.proyectointegradorc1g6.service.IUsuarioService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements IUsuarioService {

    private final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

    private UsuarioRepository usuarioRepository;

    private ModelMapper modelMapper;
    private RolRepository rolRepository;
    private PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, ModelMapper modelMapper, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        configureMapping();
    }

    public UsuarioRepository getUsuarioRepository() {
        return usuarioRepository;
    }

    @Override
    public UsuarioDtoOut registrarUsuario(UsuarioDtoInput usuarioDtoInput) throws DniDuplicadoException {

        LOGGER.info("usuario input --> {}", usuarioDtoInput.toString());
        int dni = usuarioDtoInput.getDni();
        String email = usuarioDtoInput.getEmail();
        String userName = usuarioDtoInput.getUserName();
        Usuario usuarioBuscado = usuarioRepository.findByDni(dni);
        Usuario usuarioEncontradoByEmail = usuarioRepository.findByEmail(email);
        Optional<Usuario> usuarioEncontradoByUserName = usuarioRepository.findByUserName(userName);
        if (usuarioBuscado != null) {
            throw new DniDuplicadoException("El DNI " + dni + " que intenta registrar ya existe en el sistema");
        }

        if (usuarioEncontradoByEmail != null) {
            throw new DniDuplicadoException("El email " + email + " que intenta registrar ya existe en el sistema");
        }

        if (usuarioEncontradoByUserName.isPresent()) {
            throw new DniDuplicadoException("El userName " + userName + " que intenta registrar ya existe en el sistema");
        }

        Usuario usuarioARegistrar = modelMapper.map(usuarioDtoInput, Usuario.class);

        Optional<Rol> rolUser = rolRepository.findByNombre("ROLE_USER");
        List<Rol> roles = new ArrayList<>();
        rolUser.ifPresent(roles::add);
        if (usuarioARegistrar.isEsAdmin()) {
            Optional<Rol> rolAdmin = rolRepository.findByNombre("ROLE_ADMIN");
            rolAdmin.ifPresent(roles::add);
        }
        usuarioARegistrar.setRoles(roles);
        usuarioARegistrar.setPassword(passwordEncoder.encode(usuarioARegistrar.getPassword()));

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
    public UsuarioDtoOut buscarUsuarioByUserName(String userName) throws ResourceNotFoundException {
        LOGGER.info("userName --> {}", userName);

        Usuario usuarioBuscado = usuarioRepository.findByUserName(userName).orElse(null);
        LOGGER.info("usuarioBuscado --> {}", usuarioBuscado);
        UsuarioDtoOut usuarioDtoOut = null;
        if (usuarioBuscado != null) {
            usuarioDtoOut = modelMapper.map(usuarioBuscado, UsuarioDtoOut.class);
            LOGGER.info("usuarioEncontrado --> {}", usuarioDtoOut);
        } else {
            LOGGER.info("usuarioBuscado, no existe verificar --> {}", userName);
            throw new ResourceNotFoundException("No existe registro de usuario con userName: " + userName);
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
            verificarDuplicidadUsuario(usuarioDtoInput, usuarioEncontrado);
            Usuario usuarioAAtualizar = modelMapper.map(usuarioDtoInput, Usuario.class);

            Optional<Rol> rolUser = rolRepository.findByNombre("ROLE_USER");
            List<Rol> roles = new ArrayList<>();
            rolUser.ifPresent(roles::add);
            if (usuarioAAtualizar.isEsAdmin()) {
                Optional<Rol> rolAdmin = rolRepository.findByNombre("ROLE_ADMIN");
                rolAdmin.ifPresent(roles::add);
            }
            usuarioAAtualizar.setRoles(roles);
            if (usuarioAAtualizar.getPassword() != null && !usuarioAAtualizar.getPassword().isEmpty()) {
                usuarioAAtualizar.setPassword(passwordEncoder.encode(usuarioAAtualizar.getPassword()));
            } else {
                usuarioAAtualizar.setPassword(usuarioEncontrado.getPassword());
            }

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
    public UsuarioDtoOut agregarAutoFavorito(Long usuarioId, Long autoId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Auto auto = AutoService.getAutosRepository().findById(autoId)
                .orElseThrow(() -> new IllegalArgumentException("Auto no encontrado"));

        usuario.getAutosFavoritos().add(auto);
        Usuario usuarioFav = usuarioRepository.save(usuario);
        LOGGER.info("Usuario con favoritos, con id --> {}", usuarioFav);

        UsuarioDtoOut usuarioDtoOut = modelMapper.map(usuarioFav, UsuarioDtoOut.class);
        LOGGER.info("usuarioDtoOut con favoritos, con id --> {}", usuarioDtoOut);

        return usuarioDtoOut;

    }

    @Override
    public UsuarioDtoOut eliminarAutoFavorito(Long usuarioId, Long autoId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Auto auto = AutoService.getAutosRepository().findById(autoId)
                .orElseThrow(() -> new IllegalArgumentException("Auto no encontrado"));

        if (usuario.getAutosFavoritos().contains(auto)) {
            usuario.getAutosFavoritos().remove(auto);
        } else {
            throw new IllegalArgumentException("El auto no está en la lista de favoritos del usuario");
        }

        Usuario usuarioFav = usuarioRepository.save(usuario);
        LOGGER.info("Usuario con favoritos updated, con id --> {}", usuarioFav);

        UsuarioDtoOut usuarioDtoOut = modelMapper.map(usuarioFav, UsuarioDtoOut.class);
        LOGGER.info("usuarioDtoOut con favoritos, con id --> {}", usuarioDtoOut);

        return usuarioDtoOut;

    }

    @Override
    public void eliminarAllUsuarios() {
        usuarioRepository.deleteAll();
    }

    private void verificarDuplicidadUsuario(UsuarioDtoInput usuarioDtoInput, Usuario usuarioEncontrado) {
        Usuario usuarioDuplicado = usuarioRepository.findByDni(usuarioDtoInput.getDni());
        if (usuarioDuplicado != null) {

            if (!usuarioDuplicado.getId().equals(usuarioEncontrado.getId())) {
                throw new RuntimeException(new DniDuplicadoException(
                        "EL DNI " + usuarioDtoInput.getDni() + " que intenta actualizar ya existe en otro usuario del sistema"
                ));
            }
        }
    }

    private void configureMapping() {
        modelMapper.typeMap(Usuario.class, UsuarioDtoOut.class)
                .addMappings(mapper -> mapper.map(Usuario::getRoles, UsuarioDtoOut::setRoles));
    }

}
