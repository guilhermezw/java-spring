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
    private List<UserRole> roles;

    public RegisterRequestDTO(String email, String password, List<UserRole> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
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

    public @NotNull List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(@NotNull List<UserRole> roles) {
        this.roles = roles;
    }
}
