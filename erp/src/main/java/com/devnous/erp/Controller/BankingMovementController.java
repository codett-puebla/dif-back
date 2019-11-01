package com.devnous.erp.Controller;

import com.devnous.erp.Constants.Endpoints;
import com.devnous.erp.Entity.BankingMovement;
import com.devnous.erp.Service.BankingMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoints.BANKING_MOVEMENT)
public class BankingMovementController {
    @Autowired
    @Qualifier("bankingMovementService")
    BankingMovementService bankingMovementService;

    @GetMapping("/active/company={idCompany}")
    public ResponseEntity<List<BankingMovement>> getAllActiveBankingMovements(@PathVariable("idCompany") int idCompany) {
        List<BankingMovement> bankingMovements = bankingMovementService.readAllActiveBankingMovement(idCompany);
        return new ResponseEntity<>(bankingMovements, HttpStatus.OK);
    }

    @GetMapping("/removed/company={idCompany}")
    public ResponseEntity<List<BankingMovement>> getAllRemovedBankingMovements(@PathVariable("idCompany") int idCompany) {
        List<BankingMovement> bankingMovements = bankingMovementService.readAllRemovedBankingMovement(idCompany);
        return new ResponseEntity<>(bankingMovements, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankingMovement> getBankingMovement(@PathVariable("id") int id) {
        BankingMovement bankingMovement = bankingMovementService.readBankingMovement(id);
        return new ResponseEntity<BankingMovement>(bankingMovement, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<String> insertBankingMovement(@RequestBody BankingMovement bankingMovement) {
        bankingMovementService.createBankingMovement(bankingMovement);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteSoftBankingMovement(@PathVariable("id") int id) {
        bankingMovementService.softDeleteBankingMovement(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping(path = "", consumes = "application/json")
    public ResponseEntity<String> updateBankingMovement(@RequestBody BankingMovement bankingMovement) {
        bankingMovementService.updateBankingMovement(bankingMovement);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/hard={id}")
    public ResponseEntity<String> deleteBankingMovement(@PathVariable("id") int id) {
        bankingMovementService.deleteBankingMovement(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
