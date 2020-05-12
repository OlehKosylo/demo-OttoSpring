//package com.demo1.applesson1.controllers;
//
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class ExceptionHandlerController {
//
//
//    @ExceptionHandler(RuntimeException.class)
//    public HttpEntity<String> handleInvalidDateFormatException(RuntimeException e) {
//
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}
