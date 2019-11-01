package com.devnous.erp.ServiceImpl;

import com.devnous.erp.Entity.Bank;
import com.devnous.erp.Exceptions.ResourceNotFoundException;
import com.devnous.erp.Repository.BankRepository;
import com.devnous.erp.Service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("bankService")
public class BankServiceImpl implements BankService {
    @Autowired
    @Qualifier("bankRepository")
    BankRepository bankRepository;
    private String clase = Bank.class.getSimpleName();

    @Override
    public void createBank(Bank bank) {
        bankRepository.save(bank);
    }

    @Override
    public Bank readBank(int id) {
        Bank bank = bankRepository.findById(id);
        if(bank == null){
            throw new ResourceNotFoundException(clase);
        }
        return bank;
    }

    @Override
    public List<Bank> readAllActiveBank(int idCompany) {
        return bankRepository.findByStatusAndIdCompany(1, idCompany);
    }

    @Override
    public List<Bank> readAllRemovedBank(int idCompany) {
        return bankRepository.findByStatusAndIdCompany(0, idCompany);
    }

    @Override
    public void updateBank(Bank bank) {
        if (bankRepository.findById(bank.getId()) != null) {
            bankRepository.save(bank);
        }else {
            throw new ResourceNotFoundException(clase);
        }
    }

    @Override
    public void deleteBank(int id) {
        bankRepository.deleteById(id);
    }

    @Override
    public void softDeleteBank(int id) {
        Bank bank = bankRepository.findById(id);
        if (bank != null) {
            bank.setStatus(0);
            bankRepository.save(bank);
        }else{
            throw new ResourceNotFoundException(clase);
        }
    }
}
