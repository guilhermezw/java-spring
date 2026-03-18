package com.auth.security.jwt.controller;

import com.auth.security.jwt.dto.LoginRequestDTO;
import com.auth.security.jwt.dto.RegisterRequestDTO;
import com.auth.security.jwt.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String , Object>> login(@RequestBody LoginRequestDTO dto){
        String token = authService.authenticate(dto);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message" , "Login successful." , "token" ,token));
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String , Object>> register(@RequestBody RegisterRequestDTO dto){
        authService.registrer(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message" , "Register succesful" , "success" , true));
    }
}
