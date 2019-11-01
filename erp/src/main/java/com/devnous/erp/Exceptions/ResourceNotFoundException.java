package com.devnous.erp.Exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String exception){
        super("El recurso de la clase "+exception+" no fue encontrado");
    }
}
