package com.auth.security.jwt.service;

import com.auth.security.jwt.dto.user.UserResponseDTO;
import com.auth.security.jwt.model.UserModel;
import com.auth.security.jwt.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private AutheticationService autheticationService;

    public UserService(UserRepository userRepository, AutheticationService autheticationService) {
        this.userRepository = userRepository;
        this.autheticationService = autheticationService;
    }

    public UserResponseDTO myInformation(){
        UserModel user = autheticationService.getUserAuthetication();
        return new UserResponseDTO(
                user.getEmail(),
                user.getRole()
        );
    }

    public List<UserResponseDTO> listAll(){
        return userRepository
                .findAll()
                .stream()
                .map(UserModel -> new UserResponseDTO(UserModel.getEmail() , UserModel.getRole()))
                .toList();
    }
}
