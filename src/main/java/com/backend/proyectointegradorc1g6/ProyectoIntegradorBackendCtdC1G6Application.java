package com.backend.proyectointegradorc1g6;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProyectoIntegradorBackendCtdC1G6Application {

	private static Logger LOGGER = LoggerFactory.getLogger(ProyectoIntegradorBackendCtdC1G6Application.class);

	public static void main(String[] args) {
		SpringApplication.run(ProyectoIntegradorBackendCtdC1G6Application.class, args);
		LOGGER.info("Proyecto Medic Dev  ¡¡¡¡¡¡¡¡¡¡INITIALIZED!!!!!!!!!!!... port(s): 8080");
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
