package com.devnous.erp.Controller;

import com.devnous.erp.Constants.Endpoints;
import com.devnous.erp.Entity.QuotationDetail;
import com.devnous.erp.Service.QuotationDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoints.QUOTATION_DETAIL)
public class QuotationDetailController {
    @Autowired
    @Qualifier("quotationDetailService")
    QuotationDetailService quotationDetailService;

    @GetMapping("")
    public ResponseEntity<List<QuotationDetail>> getAllQuotationDetail() {
        List<QuotationDetail> quotationDetails = quotationDetailService.readAllQuotationDetail();
        return new ResponseEntity<>(quotationDetails, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuotationDetail> getQuotationDetail(@PathVariable("id") int id) {
        QuotationDetail quotationDetail = quotationDetailService.readQuotationDetail(id);
        return new ResponseEntity<QuotationDetail>(quotationDetail, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<String> insertQuotationDetail(@RequestBody QuotationDetail quotationDetail) {
        quotationDetailService.createQuotationDetail(quotationDetail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteSoftQuotationDetail(@PathVariable("id") int id) {
        quotationDetailService.softDeleteQuotationDetail(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping(path = "", consumes = "application/json")
    public ResponseEntity<String> updateQuotationDetail(@RequestBody QuotationDetail quotationDetail) {
        quotationDetailService.updateQuotationDetail(quotationDetail);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/hard={id}")
    public ResponseEntity<String> deleteQuotationDetail(@PathVariable("id") int id) {
        quotationDetailService.deleteQuotationDetail(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
