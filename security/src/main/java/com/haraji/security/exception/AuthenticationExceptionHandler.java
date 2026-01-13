package com.haraji.security.exception;


import com.haraji.common.exception.ApiError;
import com.haraji.security.exception.authentication.UserNotCreatedException;
import com.haraji.security.exception.authentication.UserNotFoundException;
import com.haraji.security.exception.authentication.WrongPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@RequiredArgsConstructor
public class AuthenticationExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex) {
        ApiError error = new ApiError(ex);
        error.setStatus(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotCreatedException.class)
    public ResponseEntity<ApiError> handleUserNotCreated(UserNotCreatedException ex) {
        ApiError error = new ApiError(ex);
        error.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
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

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ApiError> handleBadCredentialsException(WrongPasswordException ex) {
        ApiError error = new ApiError(ex);
        error.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
