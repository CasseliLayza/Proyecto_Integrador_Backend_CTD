package com.backend.proyectointegradorc1g6.controller;

import com.backend.proyectointegradorc1g6.dto.input.UsuarioDtoInput;
import com.backend.proyectointegradorc1g6.dto.input.dtoUtils.OnCreate;
import com.backend.proyectointegradorc1g6.dto.output.UsuarioDtoOut;
import com.backend.proyectointegradorc1g6.exception.DniDuplicadoException;
import com.backend.proyectointegradorc1g6.exception.ResourceNotFoundException;
import com.backend.proyectointegradorc1g6.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(originPatterns = "*")
public class UsuarioController {
    @Autowired
    private IUsuarioService usuarioService;

    @PostMapping("/create")
    public ResponseEntity<UsuarioDtoOut> create(@Validated(OnCreate.class) @RequestBody @Valid UsuarioDtoInput usuarioDtoInput) throws DniDuplicadoException {
        return new ResponseEntity<>(usuarioService.registrarUsuario(usuarioDtoInput), HttpStatus.CREATED);
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioDtoOut> registraUsuario(@RequestBody @Valid UsuarioDtoInput usuarioDtoInput) throws DniDuplicadoException {
        usuarioDtoInput.setEsAdmin(false);
        return create(usuarioDtoInput);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UsuarioDtoOut>> listarUsuarios() {
        return new ResponseEntity<>(usuarioService.listarUsuarios(), HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<UsuarioDtoOut> buscarUsuario(@PathVariable Long id) {
        return new ResponseEntity<>(usuarioService.buscarUsuario(id), HttpStatus.OK);
    }

    @GetMapping("/find/username/{username}")
    public ResponseEntity<UsuarioDtoOut> buscarUsuarioByUserName(@PathVariable String username) throws ResourceNotFoundException {
        return new ResponseEntity<>(usuarioService.buscarUsuarioByUserName(username), HttpStatus.OK);
    }

    @PutMapping("/update/privilege/{id}")
    public ResponseEntity<UsuarioDtoOut> actualizarUsuarioUpPrivilegio(@RequestBody @Valid UsuarioDtoInput usuarioDtoInput, @PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(usuarioService.actualizarUsuario(usuarioDtoInput, id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UsuarioDtoOut> actualizarUsuario(@RequestBody @Valid UsuarioDtoInput usuarioDtoInput, @PathVariable Long id) throws ResourceNotFoundException {
        usuarioDtoInput.setEsAdmin(false);
        return actualizarUsuarioUpPrivilegio(usuarioDtoInput, id);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) throws ResourceNotFoundException {
        usuarioService.eliminarUsuario(id);
        return new ResponseEntity<>("Usuario eliminado correctamente", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("delete/users/all")
    public ResponseEntity<?> eliminarAllUsuarios() {
        usuarioService.eliminarAllUsuarios();
        return new ResponseEntity<>("Todos los usuarios fueron eliminados correctamente", HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{usuarioId}/favorites/{autoId}")
    public ResponseEntity<UsuarioDtoOut> agregarAutoFavorito(@PathVariable Long usuarioId, @PathVariable Long autoId) {
        return new ResponseEntity<>(usuarioService.agregarAutoFavorito(usuarioId, autoId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{usuarioId}/favorites/{autoId}")
    public ResponseEntity<UsuarioDtoOut> eliminarAutoFavorito(@PathVariable Long usuarioId, @PathVariable Long autoId) {
        return new ResponseEntity<>(usuarioService.eliminarAutoFavorito(usuarioId, autoId), HttpStatus.CREATED);
    }

}
