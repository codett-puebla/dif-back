package com.devnous.erp.Controller;

import com.devnous.erp.Constants.Endpoints;
import com.devnous.erp.Entity.PurchaseHeader;
import com.devnous.erp.Service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoints.PURCHASE)
public class PurchaseController {
    @Autowired
    @Qualifier("purchaseService")
    PurchaseService purchaseService;

    @GetMapping("/active")
    public ResponseEntity<List<PurchaseHeader>> getAllActivePurchaseHeaders() {
        List<PurchaseHeader> purchases = purchaseService.readAllActivePurchaseHeader();
        return new ResponseEntity<>(purchases, HttpStatus.OK);
    }

    @GetMapping("/removed")
    public ResponseEntity<List<PurchaseHeader>> getAllRemovedPurchaseHeaders() {
        List<PurchaseHeader> purchases = purchaseService.readAllRemovedPurchaseHeader();
        return new ResponseEntity<>(purchases, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseHeader> getPurchaseHeader(@PathVariable("id") int id) {
        PurchaseHeader purchase = purchaseService.readPurchaseHeader(id);
        return new ResponseEntity<PurchaseHeader>(purchase, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<String> insertPurchaseHeader(@RequestBody PurchaseHeader purchase) {
        purchaseService.createPurchaseHeader(purchase);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteSoftPurchaseHeader(@PathVariable("id") int id) {
        purchaseService.softDeletePurchaseHeader(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping(path = "", consumes = "application/json")
    public ResponseEntity<String> updatePurchaseHeader(@RequestBody PurchaseHeader purchase) {
        purchaseService.updatePurchaseHeader(purchase);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @DeleteMapping(path = "hard=/{id}")
    public ResponseEntity<String> deletePurchaseHeader(@PathVariable("id") int id) {
        purchaseService.deletePurchaseHeader(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
