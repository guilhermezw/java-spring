package com.auth.security.jwt.controller;

import com.auth.security.jwt.dto.user.UserResponseDTO;
import com.auth.security.jwt.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/private")
public class PrivateController {

    private final UserService userService;

    public PrivateController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getMessage(){
        return "Hello from private API controller";
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> listAll(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.listAll());
    }

    @GetMapping("/my-information")
    public ResponseEntity<UserResponseDTO> myInformation(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.myInformation());
    }
}
