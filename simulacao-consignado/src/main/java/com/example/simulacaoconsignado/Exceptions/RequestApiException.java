package com.example.simulacaoconsignado.Exceptions;

public class RequestApiException extends RuntimeException {
    public RequestApiException(String message) {
        super(message);
    }
}
