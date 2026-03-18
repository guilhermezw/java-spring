package com.auth.security.jwt.dto;

import com.auth.security.jwt.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class RegisterRequestDTO {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotNull
    private UserRole role;

    public RegisterRequestDTO(String email, String password, UserRole role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public @NotBlank @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @Email String email) {
        this.email = email;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    public @NotNull UserRole getRole() {
        return role;
    }

    public void setRole(@NotNull UserRole role) {
        this.role = role;
    }
}
