package com.haraji.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;


@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtProperties {

    private String secretKey;

    private long expirationMs;

    private long refreshThresholdMs;

    private long refreshExpirationDays;
    
    @Bean
    public String[] defaultPermit() {
        return new String[]{"/csrf", "/v2/api-docs", "/csrf", "/swagger/**", "/swagger-ui/**", "/swagger**", "/webjars/**", "/swagger-resources/**", "/configuration/security", "/home", "/error", "/actuator", "/actuator/**"};
    }

}
