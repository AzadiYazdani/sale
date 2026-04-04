package com.haraji.security.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@PropertySource("classpath:application.yml")
@Getter
public class SecurityConfig {

    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${jwt.refresh-threshold-ms}")
    private long jwtRefreshThresholdMs;

    @Value("${jwt.expiration-ms}")
    private long jwtTimeout;


    @Bean
    public String[] defaultPermit() {
        return new String[]{"/csrf", "/v2/api-docs", "/csrf", "/swagger/**", "/swagger-ui/**", "/swagger**", "/webjars/**", "/swagger-resources/**", "/configuration/security", "/home", "/error", "/actuator", "/actuator/**"};
    }

}
