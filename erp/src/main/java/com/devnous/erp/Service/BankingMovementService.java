package com.devnous.erp.Service;

import com.devnous.erp.Entity.BankingMovement;
import com.devnous.erp.Exceptions.ResourceNotFoundException;

import java.util.List;

public interface BankingMovementService {
    void createBankingMovement(BankingMovement bankingMovement);

    BankingMovement readBankingMovement(int id) throws ResourceNotFoundException;

    List<BankingMovement> readAllActiveBankingMovement(int idCompany);

    List<BankingMovement> readAllRemovedBankingMovement(int idCompany);

    void updateBankingMovement(BankingMovement bankingMovement) throws ResourceNotFoundException;

    void deleteBankingMovement(int id);

    void softDeleteBankingMovement(int id) throws ResourceNotFoundException;
}

