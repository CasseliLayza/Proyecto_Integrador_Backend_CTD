package com.backend.proyectointegradorc1g6.controller;

import com.backend.proyectointegradorc1g6.dto.input.AutoDtoInput;
import com.backend.proyectointegradorc1g6.dto.output.AutoDtoOut;
import com.backend.proyectointegradorc1g6.exception.MatriculaDuplicadaException;
import com.backend.proyectointegradorc1g6.exception.ResourceNotFoundException;
import com.backend.proyectointegradorc1g6.service.IAutoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/autos")
@CrossOrigin
public class AutoController {

    @Autowired
    private IAutoService autoService;


    @PostMapping("/register")
    public ResponseEntity<AutoDtoOut> registrarAuto(@RequestBody AutoDtoInput autoDtoInput) throws MatriculaDuplicadaException {
        return new ResponseEntity<>(autoService.registrarAuto(autoDtoInput), HttpStatus.CREATED);
    }

    @PostMapping(value = "/registers3", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AutoDtoOut> registrarAuto(
            @RequestPart("auto") String autoDtoInputJson,
            @RequestPart("imagenes") List<MultipartFile> imagenes,
            @RequestParam("indiceImagenPrincipal") int indiceImagenPrincipal) throws MatriculaDuplicadaException, JsonProcessingException {
        return new ResponseEntity<>(autoService.registrarAuto(autoDtoInputJson, imagenes, indiceImagenPrincipal), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<AutoDtoOut>> listarAutos() {
        return new ResponseEntity<>(autoService.listarAutos(), HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<AutoDtoOut> buscarAuto(@PathVariable Long id) {
        return new ResponseEntity<>(autoService.buscarAuto(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AutoDtoOut> actualizarAuto(@RequestBody AutoDtoInput autoDtoInput, @PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(autoService.actualizarAuto(autoDtoInput, id), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> eliminarAuto(@PathVariable Long id) throws ResourceNotFoundException {
        autoService.eliminarAuto(id);
        return new ResponseEntity<>("Auto eliminado correctamente", HttpStatus.NO_CONTENT);
    }

}
