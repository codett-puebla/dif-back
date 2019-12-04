package com.devnous.erp.Controller;

import com.devnous.erp.Constants.Endpoints;
import com.devnous.erp.Entity.Departures;
import com.devnous.erp.Service.DeparturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(Endpoints.DEPARTURES)
public class DeparturesController {

    @Autowired
    @Qualifier("departuresService")
    DeparturesService departuresService;

    @GetMapping("/active")
    public ResponseEntity<List<Departures>> getAllActiveDeparturess() {
        List<Departures> departures = departuresService.readAllActiveDepartures();
        return new ResponseEntity<>(departures, HttpStatus.OK);
    }

    @GetMapping("/removed")
    public ResponseEntity<List<Departures>> getAllRemovedDeparturess() {
        List<Departures> departures = departuresService.readAllRemovedDepartures();
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

    @GetMapping("/verify")
    public ResponseEntity<Departures> verifyExistEmail(@PathParam("folio") String folio){
        return new ResponseEntity<Departures>(departuresService.verifyFolio(folio), HttpStatus.OK);
    }

    @GetMapping("/verifyQuantity")
    public ResponseEntity<Boolean> verifyQuantity(@PathParam("quantity") Integer quantity, @PathParam("idItem") Integer idItem){
        return new ResponseEntity<Boolean>(departuresService.verifyQuantity(quantity, idItem), HttpStatus.OK);
    }

}
