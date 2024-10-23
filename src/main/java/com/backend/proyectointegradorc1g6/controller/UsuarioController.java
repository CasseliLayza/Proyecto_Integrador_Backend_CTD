package com.backend.proyectointegradorc1g6.controller;

import com.backend.proyectointegradorc1g6.dto.input.UsuarioDtoInput;
import com.backend.proyectointegradorc1g6.dto.ouput.UsuarioDtoOut;
import com.backend.proyectointegradorc1g6.exception.DniDuplicadoException;
import com.backend.proyectointegradorc1g6.exception.ResourceNotFoundException;
import com.backend.proyectointegradorc1g6.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UsuarioController {
    @Autowired
    private IUsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<UsuarioDtoOut> registraUsuario(@RequestBody UsuarioDtoInput UsuarioDtoInput) throws DniDuplicadoException {
        return new ResponseEntity<>(usuarioService.registrarUsuario(UsuarioDtoInput), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UsuarioDtoOut>> listarUsuarios() {
        return new ResponseEntity<>(usuarioService.listarUsuarios(), HttpStatus.OK);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<UsuarioDtoOut> buscarUsuario(@PathVariable Long id) {
        return new ResponseEntity<>(usuarioService.buscarUsuario(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UsuarioDtoOut> actualizarUsuario(@RequestBody UsuarioDtoInput usuarioDtoInput, @PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(usuarioService.actualizarUsuario(usuarioDtoInput, id), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) throws ResourceNotFoundException {
        usuarioService.eliminarUsuario(id);
        return new ResponseEntity<>("Usuario eliminado correctamente", HttpStatus.NO_CONTENT);
    }

    @PostMapping("delete/users/all")
    public  ResponseEntity<?> eliminarAllUsuarios(){
        usuarioService.eliminarAllUsuarios();
        return  new ResponseEntity<>("Todos los usuarios fueron eliminados correctamente",HttpStatus.NO_CONTENT);
    }

}
