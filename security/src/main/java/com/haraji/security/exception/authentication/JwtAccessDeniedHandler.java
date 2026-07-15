package com.haraji.security.exception.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haraji.common.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException ex)
            throws IOException {

        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .errorCode("FORBIDDEN")
                .message("Access denied.")
                .localizedMessage("شما مجاز به انجام این عملیات نیستید.")
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        objectMapper.writeValue(response.getWriter(), error);
    }
}
