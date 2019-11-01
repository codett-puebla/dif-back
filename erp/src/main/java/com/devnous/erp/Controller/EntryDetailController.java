package com.devnous.erp.Controller;

import com.devnous.erp.Constants.Endpoints;
import com.devnous.erp.Entity.EntryDetail;
import com.devnous.erp.Service.EntryDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoints.ENTRY_DETAIL)
public class EntryDetailController {
    @Autowired
    @Qualifier("entryDetailService")
    EntryDetailService entryDetailService;

    @GetMapping("")
    public ResponseEntity<List<EntryDetail>> getAllActiveEntryDetails() {
        List<EntryDetail> entryDetails = entryDetailService.readAllEntryDetail();
        return new ResponseEntity<>(entryDetails, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EntryDetail> getEntryDetail(@PathVariable("id") int id) {
        EntryDetail entryDetail = entryDetailService.readEntryDetail(id);
        return new ResponseEntity<EntryDetail>(entryDetail, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<String> insertEntryDetail(@RequestBody EntryDetail entryDetail) {
        entryDetailService.createEntryDetail(entryDetail);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteSoftEntryDetail(@PathVariable("id") int id) {
        entryDetailService.softDeleteEntryDetail(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping(path = "", consumes = "application/json")
    public ResponseEntity<String> updateEntryDetail(@RequestBody EntryDetail entryDetail) {
        entryDetailService.updateEntryDetail(entryDetail);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/hard={id}")
    public ResponseEntity<String> deleteEntryDetail(@PathVariable("id") int id) {
        entryDetailService.deleteEntryDetail(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
