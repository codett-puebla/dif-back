package com.devnous.erp.Repository;

import com.devnous.erp.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("transactionRepository")
public interface TransactionRepository extends JpaRepository<Transaction, Serializable> {
    Transaction findById(int id);

    List<Transaction> findByStatus(int status);
}
