package com.devnous.erp.Controller;

import com.devnous.erp.Constants.Endpoints;
import com.devnous.erp.Entity.Order;
import com.devnous.erp.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoints.ORDER)
public class OrderController {
    @Autowired
    @Qualifier("orderService")
    OrderService orderService;

    @GetMapping("/active/company={idCompany}")
    public ResponseEntity<List<Order>> getAllActiveOrders(@PathVariable("idCompany") int idCompany) {
        List<Order> orders = orderService.readAllActiveOrder(idCompany);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/removed/company={idCompany}")
    public ResponseEntity<List<Order>> getAllRemovedOrders(@PathVariable("idCompany") int idCompany) {
        List<Order> orders = orderService.readAllRemovedOrder(idCompany);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable("id") int id) {
        Order order = orderService.readOrder(id);
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<String> insertOrder(@RequestBody Order order) {
        orderService.createOrder(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteSoftOrder(@PathVariable("id") int id) {
        orderService.softDeleteOrder(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping(path = "", consumes = "application/json")
    public ResponseEntity<String> updateOrder(@RequestBody Order order) {
        orderService.updateOrder(order);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/hard={id}")
    public ResponseEntity<String> deleteOrder(@PathVariable("id") int id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
