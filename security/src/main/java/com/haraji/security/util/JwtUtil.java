package com.haraji.security.util;

import com.haraji.security.config.SecurityConfig;
import com.haraji.security.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final SecurityConfig securityConfig;
    private final Key hmacKey;

    public JwtUtil(SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
        // استفاده از Keys.hmacShaKeyFor برای تولید کلید ایمن‌تر
        byte[] keyBytes = Base64.getDecoder().decode(securityConfig.getJwtSecretKey());
        this.hmacKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String parseJwtToken(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        return createToken(claims, user.getUsername());
    }

    public Boolean validateJwtToken(String token) {
        try {
            return extractExpiration(token).after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public boolean shouldRefreshToken(String token) {
        Date expiration = extractExpiration(token);
        Date now = new Date();
        long timeUntilExpiration = expiration.getTime() - now.getTime();
        return timeUntilExpiration <= securityConfig.getJwtRefreshThresholdMs();
    }

    public String refreshToken(String oldToken) {
        String username = extractUsername(oldToken);
        Claims claims = extractAllClaims(oldToken);
        // ایجاد نقشه جدید از کلایم‌ها برای ساخت توکن جدید
        return createToken(new HashMap<>(claims), username);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) hmacKey) // استفاده از متد verifyWith
                .build()
                .parseSignedClaims(token) // استفاده از parseSignedClaims به جای parseClaimsJws
                .getPayload(); // استفاده از getPayload به جای getBody
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + securityConfig.getJwtTimeout());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(hmacKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createToken(@NotNull String username){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + securityConfig.getJwtTimeout());

        return Jwts.builder()
                .setSubject(username)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(hmacKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
