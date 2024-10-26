package com.backend.proyectointegradorc1g6.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImagenDtoOut {
    private String url;
    private boolean esPrincipal;
    private Long autoId;

    @Override
    public String toString() {
        return "ImagenDtoOut{" +
                "url='" + url + '\'' +
                ", esPrincipal=" + esPrincipal +
                ", auto=" + autoId +
                '}';
    }
}
