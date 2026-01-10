package com.haraji.security.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
//@PropertySource("classpath:application.yml")
@Getter
public class SecurityConfig {

    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${jwt.refresh-threshold-ms}")
    private long jwtRefreshThresholdMs;

    @Value("${jwt.timeout}")
    private long jwtTimeout;
}
