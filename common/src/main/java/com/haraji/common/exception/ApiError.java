package com.haraji.common.exception;

import lombok.*;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;


@Getter
@ToString
public class ApiError {

    private final LocalDateTime timestamp;
    private int status;

    private HttpStatus error;
    private String code;
    private final String message;
    private final String localizedMessage;


    public ApiError(BaseException ex) {
        timestamp = LocalDateTime.now();
        message = getMessage(ex.getMessageKey());
        localizedMessage = getLocalizedMessage(ex.getMessageKey());
        code = ex.getCode();
    }

    public ApiError(RuntimeException ex) {
        timestamp = LocalDateTime.now();
        message = ex.getMessage();
        localizedMessage = getLocalizedMessage(ex.getMessage());
    }

    public ApiError(String messageCode) {
        timestamp = LocalDateTime.now();
        message = getMessage(messageCode);
        localizedMessage = getLocalizedMessage(messageCode);
    }

    public void setStatus(HttpStatus status) {
        this.status = status.value();
    }

    public void setError(HttpStatus status) {
        this.error = status;
    }

    public String getMessage(String errorCode) {
        ResourceBundle bundle = ResourceBundle.getBundle("errormessages", new Locale("en", "US"));
        return bundle.getString(errorCode);
    }

    public String getLocalizedMessage(String errorCode) {
        ResourceBundle bundle = ResourceBundle.getBundle("errormessages", new Locale("fa", "IR"));
        return bundle.getString(errorCode);
    }

}
