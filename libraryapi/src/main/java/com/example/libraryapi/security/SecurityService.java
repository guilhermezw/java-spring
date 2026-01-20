package com.example.libraryapi.security;

import com.example.libraryapi.model.Usuario;
import com.example.libraryapi.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityService {

    private UsuarioService usuarioService;

    public SecurityService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public Usuario obterUsuarioLogado(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof CustomAuthentication customAuth){
            return customAuth.getUsuario();
        }
        return null;
    }
}
