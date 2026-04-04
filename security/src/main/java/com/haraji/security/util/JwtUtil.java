package com.haraji.security.util;


import com.haraji.security.config.SecurityConfig;
import com.haraji.security.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import jakarta.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final SecurityConfig securityConfig;
    private final Key hmacKey;

    public JwtUtil(SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
        this.hmacKey = new SecretKeySpec(Base64.getDecoder().decode(securityConfig.getJwtSecretKey()),
                SignatureAlgorithm.HS256.getJcaName());
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
        return extractExpiration(token).after(new Date());
    }

    public boolean shouldRefreshToken(String token) {
        Date expiration = extractExpiration(token);
        Date now = new Date();
        long timeUntilExpiration = expiration.getTime() - now.getTime();
        return timeUntilExpiration <= securityConfig.getJwtRefreshThresholdMs();
    }

    public String refreshToken(String oldToken) {
        String username = extractUsername(oldToken);
        Map<String, Object> claims = extractAllClaims(oldToken);

        return createToken(claims, username);
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
        return Jwts.parser().setSigningKey(hmacKey).parseClaimsJws(token).getBody();
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + securityConfig.getJwtTimeout());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, hmacKey)
                .compact();
    }

    public String createToken(@NotNull String username){
//        Instant now = Instant.now();
//        Date expiryDate = Date.from(now.plus(securityConfig.getJwtTimeout(), ChronoUnit.MILLIS));

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + securityConfig.getJwtTimeout());

        return Jwts.builder()
//                .claim(securityConfig.getUserNameString(), username)
                .setSubject(username)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, hmacKey)
                .compact();
    }

}
