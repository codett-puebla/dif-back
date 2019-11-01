package com.devnous.erp.Controller;

import com.devnous.erp.Constants.Endpoints;
import com.devnous.erp.Entity.PurchaseHeader;
import com.devnous.erp.Entity.Quotation;
import com.devnous.erp.Service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoints.QUOTATION)
public class QuotationController {
    @Autowired
    @Qualifier("quotationService")
    QuotationService quotationService;

    @GetMapping("/active/company={idCompany}")
    public ResponseEntity<List<Quotation>> getAllActiveQuotations(@PathVariable("idCompany") int idCompany) {
        List<Quotation> quotations = quotationService.readAllActiveQuotation(idCompany);
        return new ResponseEntity<>(quotations, HttpStatus.OK);
    }

    @GetMapping("/removed/company={idCompany}")
    public ResponseEntity<List<Quotation>> getAllRemovedQuotations(@PathVariable("idCompany") int idCompany) {
        List<Quotation> quotations = quotationService.readAllRemovedQuotation(idCompany);
        return new ResponseEntity<>(quotations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quotation> getQuotation(@PathVariable("id") int id) {
        Quotation quotation = quotationService.readQuotation(id);
        return new ResponseEntity<Quotation>(quotation, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<String> insertQuotation(@RequestBody Quotation quotation) {
        quotationService.createQuotation(quotation);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteSoftQuotation(@PathVariable("id") int id) {
        quotationService.softDeleteQuotation(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping(path = "", consumes = "application/json")
    public ResponseEntity<String> updateQuotation(@RequestBody Quotation quotation) {
        quotationService.updateQuotation(quotation);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/hard={id}")
    public ResponseEntity<String> deleteQuotation(@PathVariable("id") int id) {
        quotationService.deleteQuotation(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @GetMapping("/make/purchase/order={id}")
    public ResponseEntity<List<PurchaseHeader>> makePurchaseFromOrder(@PathVariable("id") int id) {
        List<PurchaseHeader> purchaseHeaders = quotationService.makePurchaseFromOrder(id);
        return new ResponseEntity<>(purchaseHeaders,HttpStatus.OK);
    }
}
