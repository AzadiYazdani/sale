package com.haraji.baseinfo.exception;


import com.haraji.common.exception.ApiError;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice()
@RequiredArgsConstructor
public class BaseInfoExceptionHandler {

    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(CityNotFoundException ex) {
        ApiError error = new ApiError(ex);
        error.setStatus(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StateNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotCreated(StateNotFoundException ex) {
        ApiError error = new ApiError(ex);
        error.setStatus(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(UsernameNotFoundException.class)
//    public ResponseEntity<ApiError> handleUsernameNotFound(UsernameNotFoundException ex) {
//        ApiError error = new ApiError(
//                HttpStatus.BAD_REQUEST,
//                LocalDateTime.now(),
//                null,
//                ex.getMessage(),
//                getLocalizedMessage(ex.getMessage())
//        );
//        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//    }

}
