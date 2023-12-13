package com.example.recommendations.config;

import com.example.recommendations.exceptions.ContentNotFoundException;
import com.example.recommendations.exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionResponse> handleValidationEncodingException(MethodArgumentTypeMismatchException exception) {

        StringBuilder exceptionMessage = new StringBuilder("Argument Type Mismatch: " + exception.getParameter().getParameterName() + " does not accept " + exception.getValue());
        if(exception.getRequiredType().isEnum()) {
            String validValues = Arrays.stream(exception.getRequiredType().getEnumConstants())
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            exceptionMessage.append(" Accepted Values are: " + validValues);
        }
        return new ResponseEntity<>(new ExceptionResponse(exceptionMessage.toString(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationEncodingException(MethodArgumentNotValidException exception) {

        StringBuilder exceptionMessage = new StringBuilder("Input is not valid for: " + exception.getParameter().getParameterName() );
        return new ResponseEntity<>(new ExceptionResponse(exceptionMessage.toString(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ExceptionResponse> handleValidationEncodingException(IOException exception) {

        StringBuilder exceptionMessage = new StringBuilder("Issue reading from source files: " + exception.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(exceptionMessage.toString(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionResponse> handleValidationEncodingException(MissingServletRequestParameterException exception) {

        StringBuilder exceptionMessage = new StringBuilder("Required parameter: " + exception.getParameterName() + " is missing!");
        return new ResponseEntity<>(new ExceptionResponse(exceptionMessage.toString(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ContentNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleValidationEncodingException(ContentNotFoundException exception) {

        StringBuilder exceptionMessage = new StringBuilder("Entity Not found");
        return new ResponseEntity<>(new ExceptionResponse(exceptionMessage.toString(), LocalDateTime.now()), HttpStatus.NOT_FOUND);
    }
}
