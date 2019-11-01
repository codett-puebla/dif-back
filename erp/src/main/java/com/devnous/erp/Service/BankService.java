package com.devnous.erp.Service;

import com.devnous.erp.Entity.Bank;
import com.devnous.erp.Exceptions.ResourceNotFoundException;

import java.util.List;

public interface BankService {
    void createBank(Bank bank);

    Bank readBank(int id) throws ResourceNotFoundException;

    List<Bank> readAllActiveBank(int idCompany);

    List<Bank> readAllRemovedBank(int idCompany);

    void updateBank(Bank bank) throws ResourceNotFoundException;

    void deleteBank(int id);

    void softDeleteBank(int id) throws ResourceNotFoundException;
}
