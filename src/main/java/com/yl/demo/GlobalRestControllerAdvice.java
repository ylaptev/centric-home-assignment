package com.yl.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class GlobalRestControllerAdvice /*extends ResponseEntityExceptionHandler*/ {

    private ResponseEntity<ErrorResponse> buildResponseEntity(HttpStatus httpStatus, String message, Exception e) {
        log.error(message, e);
        return buildResponseEntity(httpStatus, message);
    };
    private ResponseEntity<ErrorResponse> buildResponseEntity(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new ErrorResponse(httpStatus.value(), message), httpStatus);
    };

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(MethodArgumentNotValidException e) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, "Unexpected server error.", e);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception e) {
        // below object should be serialized to json

        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected server error.", e);
    }

/*
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    //protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest        request) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, "Wrong path " + request.getContextPath());
    }
*/



}
