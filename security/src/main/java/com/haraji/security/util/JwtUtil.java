
package com.haraji.security.util;

import com.haraji.security.config.JwtProperties;
import com.haraji.security.database.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    private static final String ROLE_CLAIM = "role";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;

    public JwtUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        byte[] keyBytes = Base64.getDecoder().decode(jwtProperties.getSecretKey());
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(UserEntity user) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtProperties.getExpirationMs());

        return Jwts.builder()
                .subject(user.getId().toString())
                .claim(ROLE_CLAIM, user.getRole().name())
                .id(UUID.randomUUID().toString())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    public Long validateAndExtractUserId(String token) {
        Claims claims = extractAllClaims(token);

        String subject = claims.getSubject();
        String role = claims.get(ROLE_CLAIM, String.class);

        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException("INVALID_JWT_SUBJECT");
        }

        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("INVALID_JWT_ROLE");
        }

        Long userId = Long.parseLong(subject);

        if (userId <= 0) {
            throw new IllegalArgumentException("INVALID_JWT_USER_ID");
        }

        return userId;
    }

    public boolean validateJwtToken(String token) {
        try {
            validateAndExtractUserId(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    public Long extractUserId(String token) {
        return validateAndExtractUserId(token);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String parseJwtToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header == null || !header.regionMatches(true, 0, BEARER_PREFIX, 0, BEARER_PREFIX.length())) {
            return null;
        }

        String token = header.substring(BEARER_PREFIX.length()).trim();

        return token.isEmpty() ? null : token;
    }
}
