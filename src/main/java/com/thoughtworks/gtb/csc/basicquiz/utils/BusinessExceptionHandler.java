package com.thoughtworks.gtb.csc.basicquiz.utils;

import com.thoughtworks.gtb.csc.basicquiz.model.BusinessError;
import com.thoughtworks.gtb.csc.basicquiz.model.exception.BusinessBasicException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;

@RestControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(BusinessBasicException.class)
    public ResponseEntity<BusinessError> businessBasicExceptionHandler(BusinessBasicException businessBasicException) {
        BusinessError businessError = BusinessError.builder()
            .timestamp(new Timestamp(System.currentTimeMillis()))
            .status(String.valueOf(businessBasicException.getHttpStatus().value()))
            .error(businessBasicException.getHttpStatus().getReasonPhrase())
            .message(businessBasicException.getMessage())
            .build();
        return new ResponseEntity<>(businessError, businessBasicException.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BusinessError> otherExceptionHandler(Exception exception) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        BusinessError businessError = BusinessError.builder()
            .timestamp(new Timestamp(System.currentTimeMillis()))
            .status(String.valueOf(httpStatus.value()))
            .error(httpStatus.getReasonPhrase())
            .message(exception.getMessage())
            .build();
        return new ResponseEntity<>(businessError, httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BusinessError> validExceptionHandler(MethodArgumentNotValidException e) {
        e.printStackTrace();
        HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        StringBuilder stringBuilder = new StringBuilder();
        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            stringBuilder.append(error.getDefaultMessage()).append(" ");
        }
        BusinessError businessError = BusinessError.builder()
            .timestamp(new Timestamp(System.currentTimeMillis()))
            .status(String.valueOf(httpStatus.value()))
            .error(httpStatus.getReasonPhrase())
            .message(stringBuilder.toString())
            .build();
        return new ResponseEntity<>(businessError, httpStatus);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BusinessError> requestNotReadableExceptionHandler(Exception exception) {
        HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        BusinessError businessError = BusinessError.builder()
            .timestamp(new Timestamp(System.currentTimeMillis()))
            .status(String.valueOf(httpStatus.value()))
            .error(httpStatus.getReasonPhrase())
            .message(exception.getMessage())
            .build();
        return new ResponseEntity<>(businessError, httpStatus);
    }
}
