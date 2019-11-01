package com.devnous.erp.Exceptions;

public class NoContentException extends RuntimeException{
    public NoContentException(String message, String attribute){
        super("La clase "+message+" en el atributo"+attribute+"no tiene contenido, no se puede operar");
    }
}
