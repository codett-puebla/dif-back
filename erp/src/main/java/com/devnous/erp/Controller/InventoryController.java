package com.devnous.erp.Controller;

import com.devnous.erp.Constants.Endpoints;
import com.devnous.erp.Entity.Inventory;
import com.devnous.erp.Service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoints.INVENTORY)
public class InventoryController{
    @Autowired
    @Qualifier("inventoryService")
    InventoryService inventoryService;

    @GetMapping("/active")
    public ResponseEntity<List<Inventory>> getAllActiveInventories() {
        List<Inventory> inventories = inventoryService.readAllActiveInventory();
        return new ResponseEntity<>(inventories, HttpStatus.OK);
    }

    @GetMapping("/removed")
    public ResponseEntity<List<Inventory>> getAllRemovedInventories() {
        List<Inventory> inventories = inventoryService.readAllRemovedInventory();
        return new ResponseEntity<>(inventories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventory(@PathVariable("id") int id) {
        Inventory inventory = inventoryService.readInventory(id);
        return new ResponseEntity<Inventory>(inventory, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<String> insertInventory(@RequestBody Inventory inventory) {
        inventoryService.createInventory(inventory);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteSoftInventory(@PathVariable("id") int id) {
        inventoryService.softDeleteInventory(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping(path = "", consumes = "application/json")
    public ResponseEntity<String> updateInventory(@RequestBody Inventory inventory) {
        inventoryService.updateInventory(inventory);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/hard={id}")
    public ResponseEntity<String> deleteInventory(@PathVariable("id") int id) {
        inventoryService.deleteInventory(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
