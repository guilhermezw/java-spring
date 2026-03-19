package com.auth.security.jwt.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final JwtEncoder encoder;

    public JwtService(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    public String generateToken(Authentication authentication){
        Instant now = Instant.now();
        long expiry = 3600L;

        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(authoridade -> authoridade.replace("ROLE_", ""))
                .findFirst()
                .orElse("");

        var claims = JwtClaimsSet.builder()
                .issuer("spring.security.jwt")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(authentication.getName())
                .claim("role" , role)
                .build();

        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
