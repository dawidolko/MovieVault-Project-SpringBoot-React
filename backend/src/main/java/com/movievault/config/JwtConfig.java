package com.movievault.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expirationTime}")
    private long expirationTime;

    public String generateToken(String username, String role) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims validateToken(String token) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();

        return parser.parseClaimsJws(token).getBody();
    }

    @Bean
    public String getSecretKey() {
        return secretKey;
    }
}
