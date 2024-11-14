package com.backend.proyectointegradorc1g6.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailFileDto {

    private String[] toUser;
    private String subject;
    private String message;
    MultipartFile file;

    @Override
    public String toString() {
        return "EmailFileDto{" +
                "toUser=" + Arrays.toString(toUser) +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
