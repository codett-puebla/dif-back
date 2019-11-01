package com.devnous.erp.Controller;

import com.devnous.erp.Constants.Endpoints;
import com.devnous.erp.Entity.SalesDetail;
import com.devnous.erp.Service.SalesDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoints.SALE_DETAIL)
public class SaleDetailController {
    @Autowired
    @Qualifier("salesDetailService")
    SalesDetailService salesDetailService;

    @GetMapping("")
    public ResponseEntity<List<SalesDetail>> getAllSalesDetail() {
        List<SalesDetail> salesDetails = salesDetailService.readAllSalesDetail();
        return new ResponseEntity<>(salesDetails, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesDetail> getSalesDetail(@PathVariable("id") int id) {
        SalesDetail salesDetail = salesDetailService.readSalesDetail(id);
        return new ResponseEntity<SalesDetail>(salesDetail, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<String> insertSalesDetail(@RequestBody SalesDetail salesDetail) {
        salesDetailService.createSalesDetail(salesDetail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteSoftSalesDetail(@PathVariable("id") int id) {
        salesDetailService.softDeleteSalesDetail(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping(path = "", consumes = "application/json")
    public ResponseEntity<String> updateSalesDetail(@RequestBody SalesDetail salesDetail) {
        salesDetailService.updateSalesDetail(salesDetail);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/hard={id}")
    public ResponseEntity<String> deleteSalesDetail(@PathVariable("id") int id) {
        salesDetailService.deleteSalesDetail(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
