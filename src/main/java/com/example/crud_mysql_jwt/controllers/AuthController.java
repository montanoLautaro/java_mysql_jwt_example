package com.example.crud_mysql_jwt.controllers;

import com.example.crud_mysql_jwt.dto.JwtAuthResponseDTO;
import com.example.crud_mysql_jwt.dto.LoginDTO;
import com.example.crud_mysql_jwt.models.Rol;
import com.example.crud_mysql_jwt.models.User;
import com.example.crud_mysql_jwt.repositories.RolRepository;
import com.example.crud_mysql_jwt.repositories.UserRepository;
import com.example.crud_mysql_jwt.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @PostMapping("/sign_up")
    public ResponseEntity<?> signUpUser(@RequestBody LoginDTO loginDTO){
        if (userRepository.existsByEmail(loginDTO.getEmail())){
            return new ResponseEntity<>("El usuario ya existe.", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setEmail(loginDTO.getEmail());
        user.setPassword(passwordEncoder.encode(loginDTO.getPassword()));
        user.setIs_active((byte) 1);

        Rol roles = rolRepository.findByName("ROL_ADMIN").get();

        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return new ResponseEntity<>("Registro de usuario exitoso.", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO){
        System.out.println("ENTRA?");
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // obtenemos el token del jwtTokenProvider
        String token = jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtAuthResponseDTO(token));
    }

}
