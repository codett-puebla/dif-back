package com.devnous.erp.Service;

import com.devnous.erp.Entity.Transaction;
import com.devnous.erp.Exceptions.ResourceNotFoundException;

import java.util.List;

public interface TransactionService {
    void createTransaction(Transaction transaction);

    Transaction readTransaction(int id) throws ResourceNotFoundException;

    List<Transaction> readAllActiveTransaction(int idCompany);

    List<Transaction> readAllRemovedTransaction(int idCompany);

    void updateTransaction(Transaction transaction) throws ResourceNotFoundException;

    void deleteTransaction(int id);

    void softDeleteTransaction(int id) throws ResourceNotFoundException;
}
