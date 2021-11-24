package com.example.apispringgradleb2boost.exceptionhandling;

import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        CustomError customError = new CustomError(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Gson().toJson(
                new CustomError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        ex.getLocalizedMessage())
        ));
        return new ResponseEntity<Object>(customError.getMessage(), headers, customError.getCode());
    }

    @ExceptionHandler({CustomError.class})
    public ResponseEntity<Object> handleException(CustomError customError) {
        // log exception
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<Object>(customError.getMessage(), headers, customError.getCode());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        headers.setContentType(MediaType.APPLICATION_JSON);
        CustomError customError = new CustomError(HttpStatus.BAD_REQUEST.value(), new Gson().toJson(
                new CustomError(HttpStatus.BAD_REQUEST.value(),
                        ex.getLocalizedMessage())
        ));
        return handleExceptionInternal(ex, customError.getMessage(), headers, HttpStatus.valueOf(customError.getCode()), request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        headers.setContentType(MediaType.APPLICATION_JSON);
        CustomError customError = new CustomError(HttpStatus.NOT_FOUND.value(), new Gson().toJson(
                new CustomError(HttpStatus.NOT_FOUND.value(),
                        ex.getLocalizedMessage())
        ));
        return new ResponseEntity<Object>(customError.getMessage(), headers, customError.getCode());
    }
}
