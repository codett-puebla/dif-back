package com.devnous.erp.Controller;

import com.devnous.erp.Constants.Endpoints;
import com.devnous.erp.Entity.Warehouse;
import com.devnous.erp.Service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoints.WAREHOUSE)
public class WarehouseController {
    @Autowired
    @Qualifier("warehouseService")
    WarehouseService warehouseService;

    @CrossOrigin(origins = "*")
    @GetMapping("/active")
    public ResponseEntity<List<Warehouse>> getAllActiveWarehouses() {
        List<Warehouse> warehouses = warehouseService.readAllActiveWarehouse();
        return new ResponseEntity<>(warehouses, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/removed")
    public ResponseEntity<List<Warehouse>> getAllRemovedWarehouses() {
        List<Warehouse> warehouses = warehouseService.readAllRemovedWarehouse();
        return new ResponseEntity<>(warehouses, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> getWarehouse(@PathVariable("id") int id) {
        Warehouse departures = warehouseService.readWarehouse(id);
        return new ResponseEntity<Warehouse>(departures, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/new")
    public ResponseEntity<String> insertWarehouse(@RequestBody Warehouse warehouse) {
        warehouseService.createWarehouse(warehouse);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteSoftWarehouse(@PathVariable("id") int id) {
        warehouseService.softDeleteWarehouse(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PutMapping(path = "", consumes = "application/json")
    public ResponseEntity<String> updateWarehouse(@RequestBody Warehouse warehouse) {
        warehouseService.updateWarehouse(warehouse);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(path = "/hard={id}")
    public ResponseEntity<String> deleteWarehouse(@PathVariable("id") int id) {
        warehouseService.deleteWarehouse(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/execute/transfer")
    public ResponseEntity<String> executeWarehouseTransfer(
            @RequestParam("idWarehouseOrigin") int idWarehouseOrigin,
            @RequestParam("idWarehouseDestiny") int idWarehouseDestiny,
            @RequestParam("idItem") int idItem,
            @RequestParam("quantity") int quantity) {
        warehouseService.transferWarehouse(idWarehouseOrigin, idWarehouseDestiny, idItem, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
