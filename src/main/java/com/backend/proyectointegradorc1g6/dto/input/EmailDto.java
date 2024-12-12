package com.backend.proyectointegradorc1g6.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {
    private String[] toUser;
    private String subject;
    private String message;
    private String name;
    private String logo;
    private EmailReservDto details;

    @Override
    public String toString() {
        return "EmailDto{" +
                "toUser=" + Arrays.toString(toUser) +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", name='" + name + '\'' +
                ", logo='" + logo + '\'' +
                ", details=" + details +
                '}';
    }
}
