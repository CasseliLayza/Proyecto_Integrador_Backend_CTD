package com.backend.proyectointegradorc1g6.controller;

import com.backend.proyectointegradorc1g6.dto.input.ResenaDtoInput;
import com.backend.proyectointegradorc1g6.dto.output.ResenaDtoOut;
import com.backend.proyectointegradorc1g6.exception.ResourceNotFoundException;
import com.backend.proyectointegradorc1g6.service.IResenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/reviews")
@CrossOrigin(originPatterns = "*")
public class ResenaController {

    @Autowired
    IResenaService resenaService;


    @PostMapping("/register")
    public ResponseEntity<ResenaDtoOut> registraResena(@RequestBody @Valid ResenaDtoInput resenaDtoInput) {
        return new ResponseEntity<>(resenaService.registrarResena(resenaDtoInput), HttpStatus.CREATED);
    }

    @GetMapping("/list/byauto/{autoId}")
    public ResponseEntity<List<ResenaDtoOut>> buscarResenaPorAuto(@PathVariable Long autoId) {
        return new ResponseEntity<>(resenaService.listarResenasPorAuto(autoId), HttpStatus.OK);
    }

    @GetMapping("/list/byuser/{usuarioId}")
    public ResponseEntity<List<ResenaDtoOut>> buscarResenaPorUsuario(@PathVariable Long usuarioId) {
        return new ResponseEntity<>(resenaService.listarResenasPorUsuario(usuarioId), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ResenaDtoOut>> listarResenas() {
        return new ResponseEntity<>(resenaService.listarResenas(), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> eliminarResena(@PathVariable Long id) throws ResourceNotFoundException {
        resenaService.eliminarResena(id);
        return new ResponseEntity<>("Resena eliminada correctamente", HttpStatus.NO_CONTENT);
    }


}
