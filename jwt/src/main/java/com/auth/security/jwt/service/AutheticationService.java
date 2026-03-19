package com.auth.security.jwt.service;

import com.auth.security.jwt.model.UserModel;
import com.auth.security.jwt.repository.UserRepository;
import com.auth.security.jwt.security.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AutheticationService {

    private final UserRepository userRepository;

    public AutheticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel getUserAuthetication(){
        UUID id = SecurityUtils.getUserId();
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
