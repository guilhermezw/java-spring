package com.auth.security.jwt.service;

import com.auth.security.jwt.dto.LoginRequestDTO;
import com.auth.security.jwt.dto.RegisterRequestDTO;
import com.auth.security.jwt.exception.custom.AcessoNegadoException;
import com.auth.security.jwt.model.UserModel;
import com.auth.security.jwt.repository.UserRepository;
import com.auth.security.jwt.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;


    public AuthService(JwtService jwtService, UserRepository userRepository, AuthenticationManager authenticationManager, BCryptPasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public String authenticate(LoginRequestDTO dto){
        UserModel user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!user.isActive()){
            throw new AcessoNegadoException("Your account is inactive.");
        }

        if(!passwordEncoder.matches(dto.getPassword() , user.getPassword())){
            throw new RuntimeException("Email or password invalid");
        }

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
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        userRepository.save(user);
        return user;
    }
}
