package com.backend.proyectointegradorc1g6.service.imp;

import com.backend.proyectointegradorc1g6.dto.input.AutoDtoInput;
import com.backend.proyectointegradorc1g6.dto.input.ReservaDtoInput;
import com.backend.proyectointegradorc1g6.dto.input.ReservaRsmDtoInput;
import com.backend.proyectointegradorc1g6.dto.input.UsuarioDtoInput;
import com.backend.proyectointegradorc1g6.dto.output.ReservaDtoOut;
import com.backend.proyectointegradorc1g6.entity.Auto;
import com.backend.proyectointegradorc1g6.entity.Reserva;
import com.backend.proyectointegradorc1g6.entity.Usuario;
import com.backend.proyectointegradorc1g6.exception.BadRequestException;
import com.backend.proyectointegradorc1g6.exception.ResourceNotFoundException;
import com.backend.proyectointegradorc1g6.repository.ReservaRepository;
import com.backend.proyectointegradorc1g6.service.IReservaService;
import com.backend.proyectointegradorc1g6.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ReservaService implements IReservaService {

    private final Logger LOGGER = LoggerFactory.getLogger(ReservaService.class);
    ReservaRepository reservaRepository;
    UsuarioService usuarioService;
    AutoService autoService;
    private ModelMapper modelMapper;

    public ReservaService(ReservaRepository reservaRepository, UsuarioService usuarioService, AutoService autoService, ModelMapper modelMapper) {
        this.reservaRepository = reservaRepository;
        this.usuarioService = usuarioService;
        this.autoService = autoService;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    @Override
    public ReservaDtoOut registrarReserva(ReservaDtoInput reservaDtoInput) throws BadRequestException {

        LOGGER.info("reservaDtoInput --> {}", reservaDtoInput.toString());
        Reserva reservaARegistrar = modelMapper.map(reservaDtoInput, Reserva.class);
        Usuario usuarioBuscado = usuarioService.getUsuarioRepository().findByDni(reservaARegistrar.getUsuario().getDni());
        Auto autoBuscado = AutoService.getAutosRepository().findByMatricula(reservaARegistrar.getAuto().getMatricula());
        LOGGER.info("usuarioBuscado --> {}", usuarioBuscado);
        LOGGER.info("autoBuscado --> {}", autoBuscado);

        if (usuarioBuscado == null && autoBuscado == null) {
            LOGGER.error("No fue posible registrar la reserva porque el Usuario y el Auto no se encuentran en nuestra base de datos");
            throw new BadRequestException("El Usuario y el Auto no existen en la base de datos");
        }
        if (usuarioBuscado == null) {
            LOGGER.error("No fue posible registrar la reserva porque el Usuario no se encuentra en nuestra base de datos");
            throw new BadRequestException("El Usuario no existe en la base de datos");
        }
        if (autoBuscado == null) {
            LOGGER.error("No fue posible registrar la reserva porque el Auto no se encuentra en nuestra base de datos");
            throw new BadRequestException("El Auto no existe en la base de datos");
        }

        reservaARegistrar.getAuto().setId(autoBuscado.getId());
        reservaARegistrar.getUsuario().setId(usuarioBuscado.getId());
        reservaARegistrar.getUsuario().setRoles(usuarioBuscado.getRoles());
        reservaARegistrar.getAuto().setCategorias(autoBuscado.getCategorias());
        reservaARegistrar.getAuto().setCaracteristicas(autoBuscado.getCaracteristicas());
        reservaARegistrar.getAuto().setImagenes(autoBuscado.getImagenes());
        Reserva reservaRegistrada = reservaRepository.save(reservaARegistrar);
        LOGGER.info("reservaRegistrada --> {}", reservaRegistrada);

        ReservaDtoOut reservaDtoOut = modelMapper.map(reservaRegistrada, ReservaDtoOut.class);
        LOGGER.info("reservaDtoOut --> {}", reservaDtoOut);

        return reservaDtoOut;

    }

    @Override
    public ReservaDtoOut crearReserva(ReservaRsmDtoInput reservaRsmDtoInput) {
        Long usuarioId = reservaRsmDtoInput.getUsuarioId();
        Long autoId = reservaRsmDtoInput.getAutoId();
        LocalDate fechaInicio = reservaRsmDtoInput.getFechaInicio();
        LocalDate fechaFin = reservaRsmDtoInput.getFechaFin();

        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin.");
        }

        List<Auto> autosDisponibles = AutoService.getAutosRepository().findAutosDisponibles(fechaInicio, fechaFin);
        if (autosDisponibles.stream().noneMatch(auto -> auto.getId().equals(autoId))) {
            throw new IllegalArgumentException("El auto no está disponible en las fechas seleccionadas.");
        }

        Usuario usuario = usuarioService.getUsuarioRepository().findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
        Auto auto = AutoService.getAutosRepository().findById(autoId)
                .orElseThrow(() -> new IllegalArgumentException("Auto no encontrado."));

        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setAuto(auto);
        reserva.setFechaInicio(fechaInicio);
        reserva.setFechaFin(fechaFin);
        reserva.setComentario(reservaRsmDtoInput.getComentario());
        reserva.setPrecioFinal(reservaRsmDtoInput.getPrecioFinal());
        reserva.setEstado(reservaRsmDtoInput.isEstado());
        reserva.setLugarEntrega(reservaRsmDtoInput.getLugarEntrega());
        reserva.setLugarRecogida(reservaRsmDtoInput.getLugarRecogida());

        Reserva reservaCreada = reservaRepository.save(reserva);
        LOGGER.info("reservaCreada --> {}", reservaCreada);

        ReservaDtoOut reservaDtoOut = modelMapper.map(reservaCreada, ReservaDtoOut.class);
        LOGGER.info("reservaDtoOut --> {}", reservaDtoOut);

        return reservaDtoOut;

    }

    @Override
    public List<ReservaDtoOut> listarReservas() {
        List<ReservaDtoOut> reservasDtoOuts = reservaRepository.findAll().stream()
                .map(reserva -> modelMapper.map(reserva, ReservaDtoOut.class))
                .collect(Collectors.toList());
        LOGGER.info("reservasDtoOuts --> {}", JsonPrinter.toString(reservasDtoOuts));
        return reservasDtoOuts;
    }

    @Override
    public ReservaDtoOut buscarReserva(Long id) {

        Reserva reservaBuscada = reservaRepository.findById(id).orElse(null);
        LOGGER.info("reservaBuscada --> {}", reservaBuscada);

        ReservaDtoOut reservaDtoOut = null;
        if (reservaBuscada != null) {
            reservaDtoOut = modelMapper.map(reservaBuscada, ReservaDtoOut.class);
            LOGGER.info("ReservaDtoOut --> {}", JsonPrinter.toString(reservaDtoOut));
        } else {
            LOGGER.info("Reserva no encontrada, verificar el id --> {}", id);
        }

        return reservaDtoOut;

    }

    @Override
    public ReservaDtoOut actualizarReserva(ReservaRsmDtoInput reservaRsmDtoInput, Long id) throws
            ResourceNotFoundException {

        Long usuarioId = reservaRsmDtoInput.getUsuarioId();
        Long autoId = reservaRsmDtoInput.getAutoId();
        LocalDate fechaInicio = reservaRsmDtoInput.getFechaInicio();
        LocalDate fechaFin = reservaRsmDtoInput.getFechaFin();

        Reserva reservaBuscada = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada, verificar el id --> {}" + id));

        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin.");
        }

        List<Auto> autosDisponibles = AutoService.getAutosRepository().findAutosDisponiblesExcluyendoReserva(fechaInicio, fechaFin, id);
        if (autosDisponibles.stream().noneMatch(auto -> auto.getId().equals(autoId))) {
            throw new IllegalArgumentException("El auto no está disponible en las fechas seleccionadas.");
        }

        Usuario usuario = usuarioService.getUsuarioRepository().findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
        Auto auto = AutoService.getAutosRepository().findById(autoId)
                .orElseThrow(() -> new IllegalArgumentException("Auto no encontrado."));

        reservaBuscada.setUsuario(usuario);
        reservaBuscada.setAuto(auto);
        reservaBuscada.setFechaInicio(fechaInicio);
        reservaBuscada.setFechaFin(fechaFin);
        reservaBuscada.setComentario(reservaRsmDtoInput.getComentario());
        reservaBuscada.setPrecioFinal(reservaRsmDtoInput.getPrecioFinal());
        reservaBuscada.setEstado(reservaRsmDtoInput.isEstado());
        reservaBuscada.setLugarEntrega(reservaRsmDtoInput.getLugarEntrega());
        reservaBuscada.setLugarRecogida(reservaRsmDtoInput.getLugarRecogida());

        Reserva reservaActualizada = reservaRepository.save(reservaBuscada);
        LOGGER.info("reservaCreada --> {}", reservaActualizada);

        ReservaDtoOut reservaDtoOut = modelMapper.map(reservaActualizada, ReservaDtoOut.class);
        LOGGER.info("reservaDtoOut --> {}", reservaDtoOut);

        return reservaDtoOut;

    }

    @Override
    public void eliminarReserva(Long id) throws ResourceNotFoundException {
        if (buscarReserva(id) != null) {
            reservaRepository.deleteById(id);
            LOGGER.info("Reserva eliminado con id --> {}", id);
        } else {
            //Custom exception
            throw new ResourceNotFoundException("No existe registro de Reserva con id " + id);

        }
    }

    private void configureMapping() {
        modelMapper.typeMap(ReservaDtoInput.class, Reserva.class)
                .addMappings(mapper -> mapper.map(ReservaDtoInput::getUsuario, Reserva::setUsuario))
                .addMappings(mapper -> mapper.map(ReservaDtoInput::getAuto, Reserva::setAuto));
        modelMapper.typeMap(Reserva.class, ReservaDtoInput.class)
                .addMappings(mapper -> mapper.map(Reserva::getUsuario, ReservaDtoInput::setUsuario))
                .addMappings(mapper -> mapper.map(Reserva::getAuto, ReservaDtoInput::setAuto));

    }

}
