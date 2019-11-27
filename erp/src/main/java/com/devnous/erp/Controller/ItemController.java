package com.devnous.erp.Controller;

import com.devnous.erp.Constants.Endpoints;
import com.devnous.erp.Entity.Item;
import com.devnous.erp.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoints.ITEM)
public class ItemController {
    @Autowired
    @Qualifier("itemService")
    ItemService itemService;

    @CrossOrigin(origins = "*")
    @GetMapping("/active")
    public ResponseEntity<List<Item>> getAllActiveItems() {
        List<Item> items = itemService.readAllActiveItem();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/removed")
    public ResponseEntity<List<Item>> getAllRemovedItems() {
        List<Item> items = itemService.readAllRemovedItem();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable("id") int id) {
        Item item = itemService.readItem(id);
        return new ResponseEntity<Item>(item, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/new")
    public ResponseEntity<String> insertItem(@RequestBody Item item) {
        itemService.createItem(item);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteSoftItem(@PathVariable("id") int id) {
        itemService.softDeleteItem(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PutMapping(path = "", consumes = "application/json")
    public ResponseEntity<String> updateItem(@RequestBody Item item) {
        itemService.updateItem(item);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(path = "/hard={id}")
    public ResponseEntity<String> deleteItem(@PathVariable("id") int id) {
        itemService.deleteItem(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getAllItemsActive")
    public ResponseEntity<List<Item>> getAllActiveItems(@RequestParam("idWarehouse")int idWarehouse) {
        List<Item> items = itemService.getItemsByWarehouse(idWarehouse);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/isAvailableQuantityItem")
    public boolean isAvailableQuantityItem(@RequestParam("idItem")int idItem,@RequestParam("quantity")int quantity) {
        return itemService.isAvailableQuantity(idItem,quantity);
    }
}
