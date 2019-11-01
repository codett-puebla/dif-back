package com.devnous.erp.Controller;

import com.devnous.erp.Constants.Endpoints;
import com.devnous.erp.Entity.Bank;
import com.devnous.erp.Service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoints.BANK)
public class BankController {
    @Autowired
    @Qualifier("bankService")
    BankService bankService;

    @GetMapping("/active/company={idCompany}")
    public ResponseEntity<List<Bank>> getAllActiveBanks(@PathVariable("idCompany") int idCompany) {
        List<Bank> banks = bankService.readAllActiveBank(idCompany);
        return new ResponseEntity<>(banks, HttpStatus.OK);
    }

    @GetMapping("/removed/company={idCompany}")
    public ResponseEntity<List<Bank>> getAllRemovedBanks(@PathVariable("idCompany") int idCompany) {
        List<Bank> banks = bankService.readAllRemovedBank(idCompany);
        return new ResponseEntity<>(banks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bank> getBank(@PathVariable("id") int id) {
        Bank bank = bankService.readBank(id);
        return new ResponseEntity<Bank>(bank, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<String> insertBank(@RequestBody Bank bank) {
        bankService.createBank(bank);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteSoftBank(@PathVariable("id") int id) {
        bankService.softDeleteBank(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping(path = "", consumes = "application/json")
    public ResponseEntity<String> updateBank(@RequestBody Bank bank) {
        bankService.updateBank(bank);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/hard={id}")
    public ResponseEntity<String> deleteBank(@PathVariable("id") int id) {
        bankService.deleteBank(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
