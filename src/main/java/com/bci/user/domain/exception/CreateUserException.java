package com.bci.user.domain.exception;

public class CreateUserException extends RuntimeException {

    public CreateUserException(){
        super("Error al crear usuario");
    }
}
