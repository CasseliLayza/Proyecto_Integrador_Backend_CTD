package com.backend.proyectointegradorc1g6.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Arrays;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailFileDto {
    private String[] toUser;
    @NotBlank(message = "The value subject should not be empty")
    @Size(max = 50, message = "The value subject should has max 50 characters")
    private String subject;
    @NotBlank(message = "The value message should not be empty")
    @Size(max = 200, message = "The value message should has max 200 characters for its URL")
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
