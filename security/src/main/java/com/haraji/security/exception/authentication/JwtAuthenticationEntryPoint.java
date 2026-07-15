package com.haraji.security.exception.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haraji.common.dto.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException ex)
            throws IOException, ServletException {

        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .errorCode("UNAUTHORIZED")
                .message("Unauthorized")
                .localizedMessage("احراز هویت انجام نشده است.")
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        objectMapper.writeValue(response.getWriter(), error);
    }
}
