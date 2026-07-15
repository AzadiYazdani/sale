package com.haraji.common.exception;

import com.haraji.common.constant.AppLocale;
import com.haraji.common.constant.Language;
import com.haraji.common.dto.ErrorResponse;
import com.haraji.common.util.MessageArgumentConverter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(
            BaseException ex,
            HttpServletRequest request) {

        ErrorResponse response = ErrorResponse.builder()
                .status(ex.getStatus().value())
                .errorCode(ex.getErrorCode().name())
                .message(
                        messageSource.getMessage(
                                ex.getMessageKey(),
                                MessageArgumentConverter.convert(
                                        ex.getArgs(),
                                        Language.ENGLISH
                                ),
                                AppLocale.ENGLISH
                        )
                )
                .localizedMessage(
                        messageSource.getMessage(
                                ex.getMessageKey(),
                                MessageArgumentConverter.convert(
                                        ex.getArgs(),
                                        Language.PERSIAN
                                ),
                                AppLocale.PERSIAN
                        )
                )
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(ex.getStatus())
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception ex,
            HttpServletRequest request) {

        ErrorResponse response = ErrorResponse.builder()
                .status(500)
                .errorCode("INTERNAL_SERVER_ERROR")
                .message(ex.getMessage())
                .localizedMessage("خطای داخلی سیستم")
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.internalServerError()
                .body(response);
    }

}
