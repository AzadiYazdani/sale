package com.haraji.baseinfo.exception;

import com.haraji.common.exception.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseInfoExceptionHandler {

    @ExceptionHandler(BusinessTypeNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(BusinessTypeNotFoundException ex) {
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotCreated(CityNotFoundException ex) {
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StateNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotCreated(StateNotFoundException ex) {
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
