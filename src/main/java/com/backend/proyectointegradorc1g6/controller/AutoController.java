package com.backend.proyectointegradorc1g6.controller;

import com.backend.proyectointegradorc1g6.dto.input.AutoDtoInput;
import com.backend.proyectointegradorc1g6.dto.output.AutoDtoOut;
import com.backend.proyectointegradorc1g6.exception.MatriculaDuplicadaException;
import com.backend.proyectointegradorc1g6.exception.ResourceNotFoundException;
import com.backend.proyectointegradorc1g6.service.IAutoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/autos")
@CrossOrigin(originPatterns = "*")
public class AutoController {

    @Autowired
    private IAutoService autoService;


    @PostMapping("/register")
    public ResponseEntity<AutoDtoOut> registrarAuto(@RequestBody @Valid AutoDtoInput autoDtoInput) throws MatriculaDuplicadaException {
        return new ResponseEntity<>(autoService.registrarAuto(autoDtoInput), HttpStatus.CREATED);
    }

    @PostMapping(value = "/registers3", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AutoDtoOut> registrarAutoS3(
            @RequestPart("auto") String autoDtoInputJson,
            @RequestPart("imagenes") List<MultipartFile> imagenes,
            @RequestParam("indiceImagenPrincipal") int indiceImagenPrincipal) throws MatriculaDuplicadaException, JsonProcessingException {
        return new ResponseEntity<>(autoService.registrarAutoS3(autoDtoInputJson, imagenes, indiceImagenPrincipal), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<AutoDtoOut>> listarAutos() {
        return new ResponseEntity<>(autoService.listarAutos(), HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<AutoDtoOut> buscarAuto(@PathVariable Long id) {
        return new ResponseEntity<>(autoService.buscarAuto(id), HttpStatus.OK);
    }

    @GetMapping("/find/marca/{marca}")
    public ResponseEntity<List<AutoDtoOut>> buscarAutosByMarca(@PathVariable String marca) throws ResourceNotFoundException {
        return new ResponseEntity<>(autoService.buscarAutosByMarca(marca), HttpStatus.OK);
    }

    @GetMapping("/find/available")
    public ResponseEntity<List<AutoDtoOut>> buscarAutosDisponibles(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return new ResponseEntity<>(autoService.buscarAutosDisponibles(fechaInicio, fechaFin), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AutoDtoOut> actualizarAuto(@RequestBody @Valid AutoDtoInput autoDtoInput, @PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(autoService.actualizarAuto(autoDtoInput, id), HttpStatus.OK);
    }

    @PutMapping(value = "/updates3/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AutoDtoOut> actualizarAutoS3(
            @RequestPart("auto") String autoDtoInputJson,
            @RequestPart("imagenes") List<MultipartFile> imagenes,
            @RequestParam("indiceImagenPrincipal") int indiceImagenPrincipal,
            @PathVariable Long id) throws MatriculaDuplicadaException, JsonProcessingException, ResourceNotFoundException {
        return new ResponseEntity<>(autoService.actualizarAutoS3(autoDtoInputJson, imagenes, indiceImagenPrincipal, id), HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> eliminarAuto(@PathVariable Long id) throws ResourceNotFoundException {
        autoService.eliminarAuto(id);
        return new ResponseEntity<>("Auto eliminado correctamente", HttpStatus.NO_CONTENT);
    }

}
