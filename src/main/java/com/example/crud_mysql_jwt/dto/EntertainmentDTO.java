package com.example.crud_mysql_jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntertainmentDTO {
    private Long id;

    @NotEmpty //No permite datos vacios
    @Size(min = 1, message = "El título debe tener al menos 1 caracter.")
    private String title;

    private String review;

    private byte score;

    private boolean watch_again;

    private int duration;
}
