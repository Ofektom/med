package com.ofektom.med.exception;

public class IllegalRequestException extends RuntimeException{
    public IllegalRequestException(String message) {
        super(message);
    }
}
