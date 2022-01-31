package com.bruno.bdb.exceptions;

import com.bruno.bdb.resources.exceptions.ValidationError;
import com.bruno.bdb.services.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(
            ObjectNotFoundException exception, HttpServletRequest request
    ) {
        int code = HttpStatus.NOT_FOUND.value();
        StandardError error = StandardError.builder()
                .timestamp(Instant.now())
                .statusCode(code)
                .error("Not Found")
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(code).body(error);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<StandardError> authorization(
            AuthorizationException exception, HttpServletRequest request
    ) {
        int code = HttpStatus.FORBIDDEN.value();
        StandardError error = StandardError.builder()
                .timestamp(Instant.now())
                .statusCode(code)
                .error("Forbidden")
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(code).body(error);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<StandardError> authentication(
            AuthenticationException exception, HttpServletRequest request
    ) {
        int code = HttpStatus.FORBIDDEN.value();
        StandardError error = StandardError.builder()
                .timestamp(Instant.now())
                .statusCode(code)
                .error("Forbidden")
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(code).body(error);
    }

    @ExceptionHandler(InactiveAccountException.class)
    public ResponseEntity<StandardError> inactiveAccount(
            InactiveAccountException exception, HttpServletRequest request
    ) {
        int code = HttpStatus.PRECONDITION_REQUIRED.value();
        StandardError error = StandardError.builder()
                .timestamp(Instant.now())
                .statusCode(code)
                .error("Precondition required")
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(code).body(error);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<StandardError> insufficientFunds(
            InsufficientFundsException exception, HttpServletRequest request
    ) {
        int code = HttpStatus.BAD_REQUEST.value();
        StandardError error = StandardError.builder()
                .timestamp(Instant.now())
                .statusCode(code)
                .error("Bad Request")
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(code).body(error);
    }

    @ExceptionHandler(CardDataException.class)
    public ResponseEntity<StandardError> cardData(
            CardDataException exception, HttpServletRequest request
    ) {
        int code = HttpStatus.BAD_REQUEST.value();
        StandardError error = StandardError.builder()
                .timestamp(Instant.now())
                .statusCode(code)
                .error("Bad Request")
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(code).body(error);
    }

    @ExceptionHandler(InvalidPaymentAmountException.class)
    public ResponseEntity<StandardError> invalidPaymentAmount(
            InvalidPaymentAmountException exception, HttpServletRequest request
    ) {
        int code = HttpStatus.BAD_REQUEST.value();
        StandardError error = StandardError.builder()
                .timestamp(Instant.now())
                .statusCode(code)
                .error("Bad Request")
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(code).body(error);
    }

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<StandardError> transaction(
            TransactionException exception, HttpServletRequest request
    ) {
        int code = HttpStatus.NOT_ACCEPTABLE.value();
        StandardError error = StandardError.builder()
                .timestamp(Instant.now())
                .statusCode(code)
                .error("Not Acceptable")
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(code).body(error);
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<StandardError> expiredToken(
            ExpiredTokenException exception, HttpServletRequest request
    ) {
        int code = HttpStatus.BAD_REQUEST.value();
        StandardError error = StandardError.builder()
                .timestamp(Instant.now())
                .statusCode(code)
                .error("Bad Request")
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(code).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException exception) {
        int code = HttpStatus.BAD_REQUEST.value();
        ValidationError error = ValidationError.builder()
                .timestamp(Instant.now())
                .statusCode(code)
                .message("Bad Request")
                .error(exception.getMessage())
                .build();
        for (FieldError field : exception.getFieldErrors()) {
            error.addError(field.getField(), field.getDefaultMessage());
        }
        return ResponseEntity.status(code).body(error);
    }

}
