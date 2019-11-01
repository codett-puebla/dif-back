package com.devnous.erp.ErrorHandler;

import com.devnous.erp.Exceptions.ResourceNotFoundException;
import com.devnous.erp.Exceptions.TransactionInterruptedException;
import com.devnous.erp.Exceptions.UniqueAttributeException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ErrorDetaill> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request){
        ErrorDetaill errorDetaill  = new ErrorDetaill(new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetaill, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public final ResponseEntity<ErrorDetaill> handleEmptyResultDataAccess(EmptyResultDataAccessException ex, WebRequest request){
        ErrorDetaill errorDetaill  = new ErrorDetaill(new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetaill, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UniqueAttributeException.class)
    public final ResponseEntity<ErrorDetaill> handleUniqueAttributeException(UniqueAttributeException ex, WebRequest request){
        ErrorDetaill errorDetaill  = new ErrorDetaill(new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetaill, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransactionInterruptedException.class)
    public final ResponseEntity<ErrorDetaill> handleTransactionInterruptedException(TransactionInterruptedException ex, WebRequest request){
        ErrorDetaill errorDetaill  = new ErrorDetaill(new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetaill, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetaill> handleExceptions(Exception ex, WebRequest request){
        ErrorDetaill errorDetaill  = new ErrorDetaill(new Date(),
                ex.getClass()+" -> "+ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetaill, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
