package com.yl.demo.errors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Rest Controller Advice is added to hide server side messages from end user
 * including all unsupported exceptions.
 */
@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class GlobalRestControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(MethodArgumentNotValidException e) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, "Bad request: wrong argument", e);
    }

    // empty body in request
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, "Bad request: Media Type Not Supported", e);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleMessageNotReadableException(HttpMessageNotReadableException e) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, "Bad request: Message Not Readable.", e);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, "Bad request: Method Argument Type Mismatch.", e);
    }

    // Handle All Unsupported Exceptions Here:
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedServiceException(Exception e) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected server error.", e);
    }


    private ResponseEntity<ErrorResponse> buildResponseEntity(HttpStatus httpStatus, String message, Exception e) {
        log.error(message, e);
        return buildResponseEntity(httpStatus, message);
    };

    private ResponseEntity<ErrorResponse> buildResponseEntity(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new ErrorResponse(httpStatus.value(), message), httpStatus);
    };

}
