package com.devnous.erp.Exceptions;

public class TransactionInterruptedException extends RuntimeException{
    public TransactionInterruptedException(){
        super("La operación no se finalizo, uno de los inventarios sobre el cual se realiza la transacción es nulo o no contiene recursos suficientes");
    }
}
