package com.devnous.erp.Controller;

import com.devnous.erp.Constants.Endpoints;
import com.devnous.erp.Entity.SalesHeader;
import com.devnous.erp.Service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoints.SALE)
public class SaleController {
    @Autowired
    @Qualifier("saleService")
    SaleService saleService;

    @GetMapping("/active/company={idCompany}")
    public ResponseEntity<List<SalesHeader>> getAllActiveSalesHeaders(@PathVariable("idCompany") int idCompany) {
        List<SalesHeader> salesHeaders = saleService.readAllActiveSalesHeader(idCompany);
        return new ResponseEntity<>(salesHeaders, HttpStatus.OK);
    }

    @GetMapping("/removed/company={idCompany}")
    public ResponseEntity<List<SalesHeader>> getAllRemovedSalesHeaders(@PathVariable("idCompany") int idCompany) {
        List<SalesHeader> salesHeaders = saleService.readAllRemovedSalesHeader(idCompany);
        return new ResponseEntity<>(salesHeaders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesHeader> getSalesHeader(@PathVariable("id") int id) {
        SalesHeader salesHeader = saleService.readSalesHeader(id);
        return new ResponseEntity<SalesHeader>(salesHeader, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<String> insertSalesHeader(@RequestBody SalesHeader sale) {
        saleService.createSalesHeader(sale);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteSoftSalesHeader(@PathVariable("id") int id) {
        saleService.softDeleteSales(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping(path = "", consumes = "application/json")
    public ResponseEntity<String> updateSalesHeader(@RequestBody SalesHeader sale) {
        saleService.updateSalesHeader(sale);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/hard={id}")
    public ResponseEntity<String> deleteSalesHeader(@PathVariable("id") int id) {
        saleService.deleteSalesHeader(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @GetMapping("/isValidFolio")
    public boolean isValidFolio(@RequestParam("folio") String folio, @RequestParam("series") String series, @RequestParam("idCompany") Integer idCompany) {
        return saleService.isAValidFolioAndSeries(folio, series, idCompany);
    }
}
