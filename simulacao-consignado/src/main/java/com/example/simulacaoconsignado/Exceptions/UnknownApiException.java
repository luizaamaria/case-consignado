package com.example.simulacaoconsignado.Exceptions;

public class UnknownApiException extends RuntimeException {
    public UnknownApiException(String message) {
        super(message);
    }
}
