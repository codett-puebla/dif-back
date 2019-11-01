package com.devnous.erp.ServiceImpl;

import com.devnous.erp.Entity.BankingMovement;
import com.devnous.erp.Exceptions.ResourceNotFoundException;
import com.devnous.erp.Repository.BankingMovementRepository;
import com.devnous.erp.Service.BankingMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("bankingMovementService")
public class BankingMovementServiceImpl implements BankingMovementService {
    @Autowired
    @Qualifier("bankingMovementRepository")
    BankingMovementRepository bankingMovementRepository;
    private String clase = BankingMovement.class.getSimpleName();

    @Override
    public void createBankingMovement(BankingMovement bankingMovement) {
        bankingMovementRepository.save(bankingMovement);
    }

    @Override
    public BankingMovement readBankingMovement(int id) {
        BankingMovement bankingMovement = bankingMovementRepository.findById(id);
        if (bankingMovement ==null){
            throw new ResourceNotFoundException(clase);
        }
        return bankingMovement;
    }

    @Override
    public List<BankingMovement> readAllActiveBankingMovement(int idCompany) {
        return bankingMovementRepository.findByStatusAndIdCompany(1, idCompany);
    }

    @Override
    public List<BankingMovement> readAllRemovedBankingMovement(int idCompany) {
        return bankingMovementRepository.findByStatusAndIdCompany(0, idCompany);
    }

    @Override
    public void updateBankingMovement(BankingMovement bankingMovement) throws ResourceNotFoundException {
        if (bankingMovementRepository.findById(bankingMovement.getId()) != null) {
            bankingMovementRepository.save(bankingMovement);
        } else {
            throw new ResourceNotFoundException(clase);
        }
    }

    @Override
    public void deleteBankingMovement(int id) throws ResourceNotFoundException {
        if (bankingMovementRepository.findById(id) != null) {
            bankingMovementRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(clase);
        }
    }

    @Override
    public void softDeleteBankingMovement(int id) throws ResourceNotFoundException {
        BankingMovement bankingMovement = bankingMovementRepository.findById(id);
        if (bankingMovement != null) {
            bankingMovement.setStatus(0);
            bankingMovementRepository.save(bankingMovement);
        } else {
            throw new ResourceNotFoundException(clase);
        }
    }
}
