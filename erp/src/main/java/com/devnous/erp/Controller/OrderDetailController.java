package com.devnous.erp.Controller;

import com.devnous.erp.Constants.Endpoints;
import com.devnous.erp.Entity.OrderDetail;
import com.devnous.erp.Service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoints.ORDER_DETAIL)
public class OrderDetailController {
    @Autowired
    @Qualifier("orderDetailService")
    OrderDetailService orderDetailService;

    @GetMapping("")
    public ResponseEntity<List<OrderDetail>> getAllOrderDetail() {
        List<OrderDetail> orderDetails = orderDetailService.readAllOrderDetail();
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetail> getOrderDetail(@PathVariable("id") int id) {
        OrderDetail orderDetail = orderDetailService.readOrderDetail(id);
        return new ResponseEntity<OrderDetail>(orderDetail, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<String> insertOrderDetail(@RequestBody OrderDetail orderDetail) {
        orderDetailService.createOrderDetail(orderDetail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteSoftOrderDetail(@PathVariable("id") int id) {
        orderDetailService.softDeleteOrderDetail(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping(path = "", consumes = "application/json")
    public ResponseEntity<String> updateOrderDetail(@RequestBody OrderDetail orderDetail) {
        orderDetailService.updateOrderDetail(orderDetail);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/hard={id}")
    public ResponseEntity<String> deleteOrderDetail(@PathVariable("id") int id) {
        orderDetailService.deleteOrderDetail(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
