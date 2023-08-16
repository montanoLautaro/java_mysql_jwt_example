package com.example.crud_mysql_jwt.dto;

import com.example.crud_mysql_jwt.models.Entertainment;


import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

public class UserDTO {

    private Long id_user;

    @NotEmpty(message = "El campo email es obligatorio.")
    @Email
    private String email;
    @NotEmpty(message = "El campo contraseña es obligatorio.")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres.")
    private String password;
    private byte is_active;
    private Set<Entertainment> entertainments = new HashSet<>();

}
