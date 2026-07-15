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
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final String ROLE_CLAIM = "role";
    private static final String BEARER_PREFIX = "Bearer ";
    private final SecretKey secretKey;

    public JwtUtil(JwtProperties jwtProperties) {
        byte[] keyBytes =
                Base64.getDecoder()
                        .decode(jwtProperties.getSecretKey());

        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * ساخت Access Token
     */
    public String generateAccessToken(UserEntity user) {

        Date now = new Date();
//        Date expiration = new Date(now.getTime() + jwtProperties.getExpirationMs());
        Date expiration = new Date(now.getTime());

        return Jwts.builder()
                .subject(user.getId().toString())
                .claim(ROLE_CLAIM, user.getRole().name())
                .id(UUID.randomUUID().toString())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    /**
     * اعتبارسنجی Token
     */
    public boolean validateJwtToken(String token) {

        try {
            extractAllClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }

    }

    /**
     * استخراج UserId
     */
    public Long extractUserId(String token) {
        return Long.parseLong(extractAllClaims(token).getSubject() );
    }

    /**
     * استخراج Role
     */
    public String extractRole(String token) {
        return extractAllClaims(token).get(ROLE_CLAIM, String.class);
    }

    /**
     * استخراج تاریخ انقضا
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * استخراج Claim دلخواه
     */
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(extractAllClaims(token)
        );
    }

    /**
     * آیا Token منقضی شده است؟
     */
    public boolean isExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * استخراج Claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * استخراج Token از Header
     */
    public String parseJwtToken(HttpServletRequest request) {

        String header = request.getHeader("Authorization");
        if (header == null) {
            return null;
        }

        if (!header.startsWith(BEARER_PREFIX)) {
            return null;
        }

        return header.substring(BEARER_PREFIX.length());
    }

}
