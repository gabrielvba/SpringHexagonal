package com.github.gabrielvba.ms_order_management.domain.exception;

public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException(String message) {
        super(message);
    }
}