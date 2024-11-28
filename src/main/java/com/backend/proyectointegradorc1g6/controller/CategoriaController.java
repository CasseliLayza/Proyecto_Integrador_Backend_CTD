package com.backend.proyectointegradorc1g6.controller;

import com.backend.proyectointegradorc1g6.dto.input.CategoriaDtoInput;
import com.backend.proyectointegradorc1g6.dto.output.CategoriaDtoOut;
import com.backend.proyectointegradorc1g6.exception.CategoriaEnUsoException;
import com.backend.proyectointegradorc1g6.exception.CategoriaDuplicadaException;
import com.backend.proyectointegradorc1g6.exception.ResourceNotFoundException;
import com.backend.proyectointegradorc1g6.service.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin(originPatterns = "*")
public class CategoriaController {
    @Autowired
    ICategoriaService categoriaService;


    @PostMapping("/register")
    public ResponseEntity<CategoriaDtoOut> registraUsuario(@RequestBody @Valid CategoriaDtoInput categoriaDtoInput) throws CategoriaDuplicadaException {
        return new ResponseEntity<>(categoriaService.registrarCategoria(categoriaDtoInput), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CategoriaDtoOut>> listarCategorias() {
        return new ResponseEntity<>(categoriaService.listarCategorias(), HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<CategoriaDtoOut> buscarCategoria(@PathVariable Long id) {
        return new ResponseEntity<>(categoriaService.buscarCategoria(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoriaDtoOut> actualizarCategoria(@RequestBody @Valid CategoriaDtoInput categoriaDtoInput, @PathVariable Long id) throws ResourceNotFoundException, CategoriaDuplicadaException {
        return new ResponseEntity<>(categoriaService.actualizarCategoria(categoriaDtoInput, id), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> eliminarCategoria(@PathVariable Long id) throws ResourceNotFoundException, CategoriaEnUsoException {
        categoriaService.eliminarCategoria(id);
        return new ResponseEntity<>("Categoria eliminada correctamente", HttpStatus.NO_CONTENT);
    }
}
