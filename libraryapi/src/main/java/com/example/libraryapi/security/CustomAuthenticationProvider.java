package com.example.libraryapi.security;

import com.example.libraryapi.model.Usuario;
import com.example.libraryapi.service.UsuarioService;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private UsuarioService usuarioService;
    private PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String senhaLogin = authentication.getCredentials().toString();

        Usuario usuarioEncontrado = usuarioService.obterPorLogin(login);
        if (usuarioEncontrado == null) {
            throw getErroUsuarioNaoEncontrado();
        }

        String senhaCriptografada = passwordEncoder.encode(senhaLogin);
        boolean senhaVerificar = passwordEncoder.matches(senhaLogin, senhaCriptografada);

        if(senhaVerificar){
            return new CustomAuthentication(usuarioEncontrado);
        }

        throw getErroUsuarioNaoEncontrado();

    }

    private static @NonNull UsernameNotFoundException getErroUsuarioNaoEncontrado() {
        return new UsernameNotFoundException("Usuário e/ou senha incorretos");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
