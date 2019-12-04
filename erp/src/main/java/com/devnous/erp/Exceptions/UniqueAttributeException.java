package com.devnous.erp.Exceptions;

public class UniqueAttributeException extends RuntimeException{
    public UniqueAttributeException(String exception){
        super("El/Los attributo(s) "+ exception +" de la clase ya ha sido registrado y debe(n) ser unico(s)");
    }
}
