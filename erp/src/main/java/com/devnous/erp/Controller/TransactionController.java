package com.devnous.erp.Controller;

import com.devnous.erp.Constants.Endpoints;
import com.devnous.erp.Entity.Transaction;
import com.devnous.erp.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoints.TRANSACTION)
public class TransactionController {
    @Autowired
    @Qualifier("transactionService")
    TransactionService transactionService;

    @CrossOrigin(origins = "*")
    @GetMapping("/active")
    public ResponseEntity<List<Transaction>> getAllActiveTransactions() {
        List<Transaction> transactions = transactionService.readAllActiveTransaction();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/removed")
    public ResponseEntity<List<Transaction>> getAllRemovedTransactions() {
        List<Transaction> transactions = transactionService.readAllRemovedTransaction();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable("id") int id) {
        Transaction departures = transactionService.readTransaction(id);
        return new ResponseEntity<Transaction>(departures, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<String> insertTransaction(@RequestBody Transaction transaction) {
        transactionService.createTransaction(transaction);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteSoftTransaction(@PathVariable("id") int id) {
        transactionService.softDeleteTransaction(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping(path = "", consumes = "application/json")
    public ResponseEntity<String> updateTransaction(@RequestBody Transaction transaction) {
        transactionService.updateTransaction(transaction);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/hard={id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable("id") int id) {
        transactionService.deleteTransaction(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
