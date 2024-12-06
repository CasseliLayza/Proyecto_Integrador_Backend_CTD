package com.backend.proyectointegradorc1g6.controller;

import com.backend.proyectointegradorc1g6.dto.input.ReservaDtoInput;
import com.backend.proyectointegradorc1g6.dto.input.ReservaRsmDtoInput;
import com.backend.proyectointegradorc1g6.dto.output.ReservaDtoOut;
import com.backend.proyectointegradorc1g6.exception.BadRequestException;
import com.backend.proyectointegradorc1g6.exception.ResourceNotFoundException;
import com.backend.proyectointegradorc1g6.service.IReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@CrossOrigin(originPatterns = "*")
public class ReservaController {

    @Autowired
    IReservaService reservaService;

    @PostMapping("/register")
    public ResponseEntity<ReservaDtoOut> registrarReserva(@RequestBody ReservaDtoInput reservaDtoInput) throws BadRequestException {
        return new ResponseEntity<>(reservaService.registrarReserva(reservaDtoInput), HttpStatus.CREATED);
    }

    @PostMapping("/create")
    public ResponseEntity<ReservaDtoOut> crearReserva(@RequestBody ReservaRsmDtoInput reservaRsmDtoInput) throws BadRequestException {
        return new ResponseEntity<>(reservaService.crearReserva(reservaRsmDtoInput), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ReservaDtoOut>> listarReservas() {
        return new ResponseEntity<>(reservaService.listarReservas(), HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ReservaDtoOut> buscarReserva(@PathVariable Long id) {
        return new ResponseEntity<>(reservaService.buscarReserva(id), HttpStatus.OK);
    }

    @GetMapping("/find/byuser/{usuarioId}")
    public ResponseEntity<List<ReservaDtoOut>> buscarReservasByUsuario(@PathVariable Long usuarioId) throws ResourceNotFoundException {
        return new ResponseEntity<>(reservaService.buscarReservasByUsuario(usuarioId), HttpStatus.OK);
    }

    @GetMapping("/find/byauto/{autoId}")
    public ResponseEntity<List<ReservaDtoOut>> buscarReservaByAuto(@PathVariable Long autoId) throws ResourceNotFoundException {
        return new ResponseEntity<>(reservaService.buscarReservasByAuto(autoId), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ReservaDtoOut> actualizarReserva(@RequestBody ReservaRsmDtoInput reservaDtoInput, @PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(reservaService.actualizarReserva(reservaDtoInput, id), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> eliminarReserva(@PathVariable Long id) throws ResourceNotFoundException {
        reservaService.eliminarReserva(id);
        return new ResponseEntity<>("Reserva eliminada correctamente", HttpStatus.NO_CONTENT);
    }

}
