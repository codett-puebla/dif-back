package com.devnous.erp.Controller;

import com.devnous.erp.Constants.Endpoints;
import com.devnous.erp.Entity.PurchaseDetail;
import com.devnous.erp.Service.PurchaseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoints.PURCHASE_DETAIL)
public class PurchaseDetailController {
    @Autowired
    @Qualifier("purchaseDetailService")
    PurchaseDetailService purchaseDetailService;

    @GetMapping("")
    public ResponseEntity<List<PurchaseDetail>> getAllPurchaseDetail() {
        List<PurchaseDetail> purchaseDetails = purchaseDetailService.readAllPurchaseDetail();
        return new ResponseEntity<>(purchaseDetails, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseDetail> getPurchaseDetail(@PathVariable("id") int id) {
        PurchaseDetail purchaseDetail = purchaseDetailService.readPurchaseDetail(id);
        return new ResponseEntity<PurchaseDetail>(purchaseDetail, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<String> insertPurchaseDetail(@RequestBody PurchaseDetail purchaseDetail) {
        purchaseDetailService.createPurchaseDetail(purchaseDetail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteSoftPurchaseDetail(@PathVariable("id") int id) {
        purchaseDetailService.softDeletePurchaseDetail(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping(path = "", consumes = "application/json")
    public ResponseEntity<String> updatePurchaseDetail(@RequestBody PurchaseDetail purchaseDetail) {
        purchaseDetailService.updatePurchaseDetail(purchaseDetail);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/hard={id}")
    public ResponseEntity<String> deletePurchaseDetail(@PathVariable("id") int id) {
        purchaseDetailService.deletePurchaseDetail(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
