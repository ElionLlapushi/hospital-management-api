package com.hospitalmanagement.hospital.management.api;

public class ErrorResponse {
    private int status;
    private String message;
    private java.time.LocalDateTime timestamp;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = java.time.LocalDateTime.now();
    }

    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public java.time.LocalDateTime getTimestamp() { return timestamp; }
}
