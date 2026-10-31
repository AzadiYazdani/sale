
package com.haraji.common.exception;

import com.haraji.common.constant.Language;
import com.haraji.common.dto.ErrorResponse;
import com.haraji.common.util.MessageArgumentConverter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex, HttpServletRequest request) {
        ErrorResponse response = ErrorResponse.builder()
                .status(ex.getStatus().value())
                .errorCode(ex.getErrorCode().name())
                .message(messageSource.getMessage(ex.getMessageKey(), MessageArgumentConverter.convert(ex.getArgs(), Language.ENGLISH), AppLocale.ENGLISH))
                .localizedMessage(messageSource.getMessage(ex.getMessageKey(), MessageArgumentConverter.convert(ex.getArgs(), Language.PERSIAN), AppLocale.PERSIAN))
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(ex.getStatus()).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "Invalid request.", "درخواست نامعتبر است.", request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.FORBIDDEN, "ACCESS_DENIED", "Access denied.", "دسترسی غیرمجاز است.", request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest request) {
        logger.error("Unexpected application error", ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "An unexpected error occurred.", "خطای داخلی سیستم رخ داده است.", request);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String errorCode, String message, String localizedMessage, HttpServletRequest request) {
        ErrorResponse response = ErrorResponse.builder()
                .status(status.value())
                .errorCode(errorCode)
                .message(message)
                .localizedMessage(localizedMessage)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(status).body(response);
    }
}
