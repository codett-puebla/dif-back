package com.devnous.erp.Controller;

import com.devnous.erp.Constants.Endpoints;
import com.devnous.erp.Entity.Staff;
import com.devnous.erp.Service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoints.STAFF)
public class StaffController {
    @Autowired
    @Qualifier("staffService")
    StaffService staffService;

    @GetMapping("/active/company={idCompany}")
    public ResponseEntity<List<Staff>> getAllActiveStaffs(@PathVariable("idCompany") int idCompany) {
        List<Staff> staffs = staffService.readAllActiveStaff(idCompany);
        return new ResponseEntity<>(staffs, HttpStatus.OK);
    }

    @GetMapping("/removed/company={idCompany}")
    public ResponseEntity<List<Staff>> getAllRemovedStaffs(@PathVariable("idCompany") int idCompany) {
        List<Staff> staffs = staffService.readAllRemovedStaff(idCompany);
        return new ResponseEntity<>(staffs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Staff> getStaff(@PathVariable("id") int id) {
        Staff departures = staffService.readStaff(id);
        return new ResponseEntity<Staff>(departures, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<String> insertStaff(@RequestBody Staff staff) {
        staffService.createStaff(staff);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteSoftStaff(@PathVariable("id") int id) {
        staffService.softDeleteStaff(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping(path = "", consumes = "application/json")
    public ResponseEntity<String> updateStaff(@RequestBody Staff staff) {
        staffService.updateStaff(staff);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/hard={id}")
    public ResponseEntity<String> deleteStaff(@PathVariable("id") int id) {
        staffService.deleteStaff(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
