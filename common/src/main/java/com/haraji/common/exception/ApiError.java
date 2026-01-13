package com.haraji.common.exception;

import lombok.*;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;


@Getter
@ToString
public class ApiError {

    private HttpStatus status;
    private LocalDateTime timestamp;
    private String code;
    private String message;
    private String localizedMessage;


    public ApiError(BaseException ex){
        timestamp = LocalDateTime.now();
        message= getMessage(ex.getMessageKey());
        localizedMessage = getLocalizedMessage(ex.getMessageKey());
        code = ex.getCode();
    }
    public ApiError(RuntimeException ex){
        timestamp = LocalDateTime.now();
        message= ex.getMessage();
        localizedMessage = getLocalizedMessage(ex.getMessage());
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
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
