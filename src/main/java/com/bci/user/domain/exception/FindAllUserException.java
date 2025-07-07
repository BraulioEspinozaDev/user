package com.bci.user.domain.exception;

public class FindAllUserException extends RuntimeException {

    public FindAllUserException(){
        super("Error al obtener listado de usuarios");
    }
}
