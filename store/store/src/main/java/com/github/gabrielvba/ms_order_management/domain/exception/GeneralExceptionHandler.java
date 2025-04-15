package com.github.gabrielvba.ms_order_management.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GeneralExceptionHandler {
  
//  @Override
//  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
//      HttpStatus status, WebRequest request) {
//    return super.handleMethodArgumentNotValid(ex, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
//  }

  @ExceptionHandler(ProductItemException.class)
  private ResponseEntity<Object> invalidProduct(ProductItemException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ExceptionDTO(HttpStatus.NOT_FOUND.toString(), ex.getMessage()));
  }
  
  @ExceptionHandler(InvalidStatusException.class)
  private ResponseEntity<Object> invalidRequest(InvalidStatusException ex) {
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
            .body(new ExceptionDTO(HttpStatus.UNPROCESSABLE_ENTITY.toString(), ex.getMessage()));
  }
}
