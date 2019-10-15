package com.gohenry.coding.challenge.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String SERVICE_NAME = "GoHenry Coding Challenge";

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(ErrorDetails.builder()
                .service(SERVICE_NAME)
                .timestamp(System.currentTimeMillis())
                .message("Content-Type should be set to application/json")
                .details(request.getDescription(false))
                .build(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(ErrorDetails.builder()
                .service(SERVICE_NAME)
                .timestamp(System.currentTimeMillis())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .build(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(ErrorDetails.builder()
                .service(SERVICE_NAME)
                .timestamp(System.currentTimeMillis())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ ParentExistedException.class, ParentNotFoundException.class, ChildNotFoundException.class })
    public ResponseEntity<ErrorDetails> handleBadRequestException(RuntimeException exception, WebRequest request) {
        return new ResponseEntity<>(ErrorDetails.builder()
                .service(SERVICE_NAME)
                .timestamp(System.currentTimeMillis())
                .message(exception.getMessage())
                .details(request.getDescription(false))
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DatabaseConnectionException.class)
    public ResponseEntity<ErrorDetails> handleBadRequestException(DatabaseConnectionException exception, WebRequest request) {
        return new ResponseEntity<>(ErrorDetails.builder()
                .service(SERVICE_NAME)
                .timestamp(System.currentTimeMillis())
                .message(exception.getMessage())
                .details(request.getDescription(false))
                .build(), HttpStatus.SERVICE_UNAVAILABLE);
    }

}
