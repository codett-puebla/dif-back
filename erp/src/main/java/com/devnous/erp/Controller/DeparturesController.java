package com.devnous.erp.Controller;

import com.devnous.erp.Constants.Endpoints;
import com.devnous.erp.Entity.Departures;
import com.devnous.erp.Service.DeparturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoints.DEPARTURES)
public class DeparturesController {

    @Autowired
    @Qualifier("departuresService")
    DeparturesService departuresService;

    @GetMapping("/active/company={idCompany}")
    public ResponseEntity<List<Departures>> getAllActiveDeparturess(@PathVariable("idCompany") int idCompany) {
        List<Departures> departures = departuresService.readAllActiveDepartures(idCompany);
        return new ResponseEntity<>(departures, HttpStatus.OK);
    }

    @GetMapping("/removed/company={idCompany}")
    public ResponseEntity<List<Departures>> getAllRemovedDeparturess(@PathVariable("idCompany") int idCompany) {
        List<Departures> departures = departuresService.readAllRemovedDepartures(idCompany);
        return new ResponseEntity<>(departures, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Departures> getDepartures(@PathVariable("id") int id) {
        Departures departures = departuresService.readDepartures(id);
        return new ResponseEntity<Departures>(departures, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<String> insertDepartures(@RequestBody Departures departures) {
        departuresService.createDeparture(departures);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteSoftDepartures(@PathVariable("id") int id) {
        departuresService.softDeleteDeparture(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping(path = "", consumes = "application/json")
    public ResponseEntity<String> updateDepartures(@RequestBody Departures departures) {
        departuresService.updateDepartures(departures);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/hard={id}")
    public ResponseEntity<String> deleteDepartures(@PathVariable("id") int id) {
        departuresService.deleteDepartures(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
