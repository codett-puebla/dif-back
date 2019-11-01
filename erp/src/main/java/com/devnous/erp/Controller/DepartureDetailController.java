package com.devnous.erp.Controller;

import com.devnous.erp.Constants.Endpoints;
import com.devnous.erp.Entity.DepartureDetail;
import com.devnous.erp.Service.DepartureDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoints.DEPARTURES_DETAIL)
public class DepartureDetailController {
    @Autowired
    @Qualifier("departureDetailService")
    DepartureDetailService departureDetailService;

    @GetMapping("")
    public ResponseEntity<List<DepartureDetail>> getAllDepartureDetail() {
        List<DepartureDetail> departures = departureDetailService.readAllDepartureDetail();
        return new ResponseEntity<>(departures, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartureDetail> getDepartureDetail(@PathVariable("id") int id) {
        DepartureDetail departures = departureDetailService.readDepartureDetail(id);
        return new ResponseEntity<DepartureDetail>(departures, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<String> insertDepartureDetail(@RequestBody DepartureDetail departureDetail) {
        departureDetailService.createDepartureDetail(departureDetail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteSoftDepartureDetail(@PathVariable("id") int id) {
        departureDetailService.softDeleteDepartureDetail(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping(path = "", consumes = "application/json")
    public ResponseEntity<String> updateDepartureDetail(@RequestBody DepartureDetail departureDetail) {
        departureDetailService.updateDepartureDetail(departureDetail);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/hard={id}")
    public ResponseEntity<String> deleteDepartureDetail(@PathVariable("id") int id) {
        departureDetailService.deleteDepartureDetail(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
