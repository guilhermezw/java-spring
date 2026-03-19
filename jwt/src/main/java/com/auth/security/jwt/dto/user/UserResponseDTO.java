package com.auth.security.jwt.dto.user;

import com.auth.security.jwt.model.UserRole;

public class UserResponseDTO {
    private String email;
    private UserRole role;

    public UserResponseDTO(String email, UserRole role) {
        this.email = email;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
