package com.bci.user.domain.exception;

public class EmailException extends RuntimeException {

    public EmailException() {
        super("El correo ya registrado");
    }
}
