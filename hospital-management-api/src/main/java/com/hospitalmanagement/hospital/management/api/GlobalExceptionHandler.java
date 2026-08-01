package com.hospitalmanagement.hospital.management.api;

@org.springframework.web.bind.annotation.ControllerAdvice
public class GlobalExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ResourceNotFoundException.class)
    public org.springframework.http.ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(404, ex.getMessage());
        return new org.springframework.http.ResponseEntity<>(error, org.springframework.http.HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public org.springframework.http.ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        ErrorResponse error = new ErrorResponse(500, ex.getMessage());
        return new org.springframework.http.ResponseEntity<>(error, org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
