package com.backend.proyectointegradorc1g6.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImagenDtoInput {
    @NotBlank(message = "The value url should not be empty")
    private String url;
    private boolean esPrincipal;
}
