package com.backend.proyectointegradorc1g6.controller;

import com.backend.proyectointegradorc1g6.dto.input.CaracteristicaDtoInput;
import com.backend.proyectointegradorc1g6.dto.output.CaracteristicaDtoOut;
import com.backend.proyectointegradorc1g6.exception.CaracteristicaDuplicadaException;
import com.backend.proyectointegradorc1g6.exception.CaracteristicaEnUsoException;
import com.backend.proyectointegradorc1g6.exception.ResourceNotFoundException;
import com.backend.proyectointegradorc1g6.service.ICaracteristicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/characteristics")
@CrossOrigin(originPatterns = "*")
public class CaracteristicaController {

    @Autowired
    private ICaracteristicaService caracteristicaService;


    @PostMapping("/register")
    public ResponseEntity<CaracteristicaDtoOut> registraCaracteristica(@RequestBody @Valid CaracteristicaDtoInput caracteristicaDtoInput) throws CaracteristicaDuplicadaException {
        return new ResponseEntity<>(caracteristicaService.registrarCaracteristica(caracteristicaDtoInput), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CaracteristicaDtoOut>> listarCaracteristicas() {
        return new ResponseEntity<>(caracteristicaService.listarCaracteristicas(), HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<CaracteristicaDtoOut> buscarCaracteristica(@PathVariable Long id) {
        return new ResponseEntity<>(caracteristicaService.buscarCaracteristica(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CaracteristicaDtoOut> actualizarCaracteristica(@RequestBody @Valid CaracteristicaDtoInput caracteristicaDtoInput, @PathVariable Long id) throws ResourceNotFoundException, CaracteristicaDuplicadaException {
        return new ResponseEntity<>(caracteristicaService.actualizarCaracteristica(caracteristicaDtoInput, id), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> eliminarCaracteristica(@PathVariable Long id) throws ResourceNotFoundException, CaracteristicaEnUsoException {
        caracteristicaService.eliminarCaracteristica(id);
        return new ResponseEntity<>("Caracteristica eliminada correctamente", HttpStatus.NO_CONTENT);
    }
}
