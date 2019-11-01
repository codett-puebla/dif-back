package com.devnous.erp.ServiceImpl;

import com.devnous.erp.Entity.Transaction;
import com.devnous.erp.Exceptions.ResourceNotFoundException;
import com.devnous.erp.Repository.TransactionRepository;
import com.devnous.erp.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("transactionService")
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    @Qualifier("transactionRepository")
    TransactionRepository transactionRepository;
    private String clase = Transaction.class.getSimpleName();

    @Override
    public void createTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public Transaction readTransaction(int id) {
        Transaction transaction = transactionRepository.findById(id);
        if(transaction == null){
            throw new ResourceNotFoundException(clase);
        }
        return transaction;
    }

    @Override
    public List<Transaction> readAllActiveTransaction(int idCompany) {
        return transactionRepository.findByStatusAndIdCompany(1, idCompany);
    }

    @Override
    public List<Transaction> readAllRemovedTransaction(int idCompany) {
        return transactionRepository.findByStatusAndIdCompany(0, idCompany);
    }

    @Override
    public void updateTransaction(Transaction transaction) {
        if (transactionRepository.findById(transaction.getId()) != null) {
            transactionRepository.save(transaction);
        }else{
            throw new ResourceNotFoundException(clase);
        }
    }

    @Override
    public void deleteTransaction(int id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public void softDeleteTransaction(int id) {
        Transaction transaction = transactionRepository.findById(id);
        if (transaction != null) {
            transaction.setStatus(0);
            transactionRepository.save(transaction);
        }else{
            throw new ResourceNotFoundException(clase);
        }
    }
}
