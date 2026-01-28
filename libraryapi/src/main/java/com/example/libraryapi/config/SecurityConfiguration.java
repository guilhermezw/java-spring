package com.example.libraryapi.config;

import com.example.libraryapi.security.CustomUserDetailsService;
import com.example.libraryapi.security.JwtCustomAuthenticationFilter;
import com.example.libraryapi.security.LoginSocialSuccessHandler;
import com.example.libraryapi.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true , jsr250Enabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http , LoginSocialSuccessHandler successHandler , JwtCustomAuthenticationFilter jwtCustomAuthenticationFilter) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .formLogin(configurer ->{
                  configurer.loginPage("/login");
                 })
                // .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login/**").permitAll();
                    // authorize.requestMatchers(HttpMethod.POST , "/autores/**").hasRole("ADMIN");
                    // authorize.requestMatchers(HttpMethod.DELETE , "/autores/**").hasRole("ADMIN");
                    // authorize.requestMatchers(HttpMethod.PUT , "/autores/**").hasRole("ADMIN");
                    // authorize.requestMatchers(HttpMethod.GET , "/autores/**").hasAnyRole("USER" , "ADMIN");
                    authorize.requestMatchers(HttpMethod.POST , "/usuarios/**").permitAll();
                    // authorize.requestMatchers("/autores/**").hasRole("ADMIN"); // Selecionar apenas uma ROLE
                    // authorize.requestMatchers("/livros/**").hasAnyRole("USER" , "ADMIN"); // Selecionar varias ROLE
                    authorize.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> {
                    oauth2
                            .loginPage("/login")
                            .successHandler(successHandler);
                })
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .addFilterAfter(jwtCustomAuthenticationFilter , BearerTokenAuthenticationFilter.class)
                .build();
    }

    /**
     *
     * @Bean
     *     public PasswordEncoder passwordEncoder (){
     *         return new BCryptPasswordEncoder(10);
     *     }
     */


    // @Bean
    public UserDetailsService userDetailsService (UsuarioService usuarioService){

        /**
         * UserDetails user1 = User.builder()
         *                 .username("usuario")
         *                 .password(encoder.encode("2233"))
         *                 .roles("USER")
         *                 .build();
         *
         *         UserDetails user2 = User.builder()
         *                 .username("admin")
         *                 .password(encoder.encode("3322"))
         *                 .roles("ADMIN")
         *                 .build();
         *
         *   return  new InMemoryUserDetailsManager(user1 , user2);
         */

        return new CustomUserDetailsService(usuarioService);
    }

    // Configura o prefixo Role
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    // Configura, No Token JWT, O prefixo Scope
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtGrantedAuthoritiesConverter authoritiesConverter =
                new JwtGrantedAuthoritiesConverter();

        authoritiesConverter.setAuthorityPrefix(""); // remove SCOPE_
        authoritiesConverter.setAuthoritiesClaimName("scope");
        // ou "roles", depende do seu JWT

        JwtAuthenticationConverter converter =
                new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);

        return converter;
    }

}
