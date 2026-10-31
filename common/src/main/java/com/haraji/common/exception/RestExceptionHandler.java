
package com.haraji.common.exception;

import com.haraji.common.constant.ErrorCode;
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

        return buildErrorResponse(
                ex.getStatus(),
                ex.getErrorCode(),
                ex.getMessageKey(),
                ex.getArgs(),
                request
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {

        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                ErrorCode.BAD_REQUEST,
                "bad.request",
                null,
                request
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {

        return buildErrorResponse(
                HttpStatus.FORBIDDEN,
                ErrorCode.ACCESS_DENIED,
                "access.denied",
                null,
                request
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest request) {

        logger.error("Unexpected application error", ex);

        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_SERVER_ERROR,
                "internal.server.error",
                null,
                request
        );
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, ErrorCode errorCode, String messageKey, Object[] args, HttpServletRequest request) {

        ErrorResponse response = ErrorResponse.builder()
                .status(status.value())
                .errorCode(errorCode.name())
                .message(messageSource.getMessage(messageKey, MessageArgumentConverter.convert(args, Language.ENGLISH), AppLocale.ENGLISH))
                .localizedMessage(messageSource.getMessage(messageKey, MessageArgumentConverter.convert(args, Language.PERSIAN), AppLocale.PERSIAN))
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(status).body(response);
    }
}
