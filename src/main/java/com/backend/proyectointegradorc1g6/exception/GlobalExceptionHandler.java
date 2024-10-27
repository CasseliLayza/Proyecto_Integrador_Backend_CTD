package com.backend.proyectointegradorc1g6.exception;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> manejarResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("mensaje", "Recurso no encontrado: " + resourceNotFoundException.getMessage());

        return mensaje;
    }

    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> manejarBadRequestException(BadRequestException badRequestException) {
        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("mensaje", "Recurso no registrado: " + badRequestException.getMessage());

        return mensaje;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> manejarMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> mensaje = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(e -> {
            String nombreCampo = ((FieldError) e).getField();
            String mensajeError = e.getDefaultMessage();
            mensaje.put(nombreCampo, mensajeError);
        });

        return mensaje;
    }

    @ExceptionHandler({DniDuplicadoException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> manejarDniDuplicadoException(DniDuplicadoException dniDuplicadoException) {
        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("mensaje", "Recurso no registrado: " + dniDuplicadoException.getMessage());

        return mensaje;
    }

    @ExceptionHandler({MatriculaDuplicadaException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> manejarMatriculaDuplicadaException(MatriculaDuplicadaException matriculaDuplicadaException) {
        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("mensaje", "Recurso no registrado: " + matriculaDuplicadaException.getMessage());

        return mensaje;
    }


    @ExceptionHandler({ReferentialIntegrityConstraintViolationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> manejarReferentialIntegrityConstraintViolationExceptionn(ReferentialIntegrityConstraintViolationException referentialIntegrityConstraintViolationException) {
        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("mensaje", "Recurso no eliminado: " + referentialIntegrityConstraintViolationException.getMessage());

        return mensaje;
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> manejarIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("mensaje", "Recurso no registrado: " + illegalArgumentException.getMessage());

        return mensaje;
    }

    @ExceptionHandler({IssuePutObjectException.class})
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    public Map<String, String> manejarIssuePutObjectException(IssuePutObjectException issuePutObjectException) {
        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("mensaje", "Recurso no registrado: " + issuePutObjectException.getMessage());

        return mensaje;
    }

    @ExceptionHandler({JsonProcessingException.class})
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public Map<String, String> manejarJsonProcessingException(JsonProcessingException jsonProcessingException) {
        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("mensaje", "Recurso no registrado: " + jsonProcessingException.getMessage());

        return mensaje;
    }

}
