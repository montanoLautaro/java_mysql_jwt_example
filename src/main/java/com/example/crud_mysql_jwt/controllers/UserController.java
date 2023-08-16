package com.example.crud_mysql_jwt.controllers;
import com.example.crud_mysql_jwt.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RequestMapping("/api/users")
@RestController
public class UserController {


    @PostMapping
    public ResponseEntity<UserDTO> createUser(){
        //TODO
        return null;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public void deleteUser(){
        
    }

}
