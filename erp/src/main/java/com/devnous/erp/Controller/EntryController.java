package com.devnous.erp.Controller;

import com.devnous.erp.Constants.Endpoints;
import com.devnous.erp.Entity.Entry;
import com.devnous.erp.Service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(Endpoints.ENTRY)
public class EntryController {
    @Autowired
    @Qualifier("entryService")
    EntryService entryService;

    @GetMapping("/active")
    public ResponseEntity<List<Entry>> getAllActiveEntrys() {
        List<Entry> entrys = entryService.readAllActiveEntrys();
        return new ResponseEntity<>(entrys, HttpStatus.OK);
    }

    @GetMapping("/removed")
    public ResponseEntity<List<Entry>> getAllRemovedEntrys() {
        List<Entry> entrys = entryService.readAllRemovedEntrys();
        return new ResponseEntity<>(entrys, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entry> getEntry(@PathVariable("id") int id) {
        Entry entry = entryService.readEntrys(id);
        return new ResponseEntity<Entry>(entry, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<String> insertEntry(@RequestBody Entry entry) {
        entryService.createEntry(entry);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteSoftEntry(@PathVariable("id") int id) {
        entryService.softDeleteEntry(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping(path = "", consumes = "application/json")
    public ResponseEntity<String> updateEntry(@RequestBody Entry entry) {
        entryService.updateEntrys(entry);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/hard={id}")
    public ResponseEntity<String> deleteEntry(@PathVariable("id") int id) {
        entryService.deleteEntrys(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
    @GetMapping("/verify")
    public ResponseEntity<Entry> verifyExistEmail(@PathParam("folio") String folio){
        return new ResponseEntity<Entry>(entryService.verifyFolio(folio), HttpStatus.OK);
    }

}
