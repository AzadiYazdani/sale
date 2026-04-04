package com.haraji.security.exception;


import com.haraji.common.exception.ApiError;
import com.haraji.security.exception.authentication.UserNotCreatedException;
import com.haraji.security.exception.authentication.UserNotFoundException;
import com.haraji.security.exception.authentication.WrongPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@RequiredArgsConstructor
public class AuthenticationExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex) {
        ApiError apiError = new ApiError(ex);
        apiError.setStatus(HttpStatus.NOT_FOUND);
        apiError.setError(HttpStatus.NOT_FOUND);
        return new ResponseEntity<> (apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotCreatedException.class)
    public ResponseEntity<ApiError> handleUserNotCreated(UserNotCreatedException ex) {
        ApiError apiError = new ApiError(ex);
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setError(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<> (apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ AuthenticationCredentialsNotFoundException.class })
    public ResponseEntity<ApiError> handleAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException ex) {
        ApiError apiError = new ApiError("credential.error");
        apiError.setStatus(HttpStatus.UNAUTHORIZED);
        apiError.setError(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<> (apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ AccessDeniedException.class})
    public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex) {
        ApiError apiError = new ApiError("credential.error");
        apiError.setStatus(HttpStatus.UNAUTHORIZED);
        apiError.setError(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<> (apiError, HttpStatus.UNAUTHORIZED);
    }

//    @ExceptionHandler({ AccessDeniedException.class, AuthenticationCredentialsNotFoundException.class })
//    public ResponseEntity<Object> handleAccessDeniedException(
//            Exception ex, WebRequest request) {
//        return new ResponseEntity<ApiError>(
//                "manan", new HttpHeaders(), HttpStatus.FORBIDDEN);
//    }


//    @ExceptionHandler(UserNotCreatedException.class)
//    public ResponseEntity<ApiError> handleUserNotCreated(UserNotCreatedException ex) {
//        ApiError apiError = new ApiError(ex);
//        apiError.setStatus(HttpStatus.BAD_REQUEST);
//        return new ResponseEntity<> (apiError, HttpStatus.BAD_REQUEST);
//    }

//    @ExceptionHandler(UsernameNotFoundException.class)
//    public ResponseEntity<ApiError> handleUsernameNotFound(UsernameNotFoundException ex) {
//        ApiError apiError = new ApiError(
//                HttpStatus.BAD_REQUEST,
//                LocalDateTime.now(),
//                null,
//                ex.getMessage(),
//                getLocalizedMessage(ex.getMessage())
//        );
//        return new ResponseEntity<> (apiError, HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ApiError> handleBadCredentialsException(WrongPasswordException ex) {
        ApiError apiError = new ApiError(ex);
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setError(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<> (apiError, HttpStatus.BAD_REQUEST);
    }
}
