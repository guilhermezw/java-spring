package com.auth.security.jwt.security;

import com.auth.security.jwt.exception.custom.UsuarioNaoAutenticadoException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public class SecurityUtils {

    public static UUID getUserId(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof Jwt jwt)) {
            throw new UsuarioNaoAutenticadoException("User not authenticated or invalid token");
        }
        String userId = jwt.getSubject();
        return UUID.fromString(userId);
    }
}
