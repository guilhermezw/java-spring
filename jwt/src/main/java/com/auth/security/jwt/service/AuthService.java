package com.auth.security.jwt.service;

import com.auth.security.jwt.dto.LoginRequestDTO;
import com.auth.security.jwt.dto.RegisterRequestDTO;
import com.auth.security.jwt.model.UserModel;
import com.auth.security.jwt.repository.UserRepository;
import com.auth.security.jwt.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public AuthService(JwtService jwtService, UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    public String authenticate(LoginRequestDTO dto){
        var authToken =  new UsernamePasswordAuthenticationToken(
                dto.getEmail(),
                dto.getPassword()
        );

        var authentication = authenticationManager.authenticate(authToken);
        return jwtService.generateToken(authentication);
    }

    public UserModel registrer(RegisterRequestDTO dto){
        if(userRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new RuntimeException("User not found");
        }
        UserModel user = new UserModel();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRoles(dto.getRoles());
        userRepository.save(user);
        return user;
    }
}
