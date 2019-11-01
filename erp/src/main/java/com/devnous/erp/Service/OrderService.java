package com.devnous.erp.Service;

import com.devnous.erp.Entity.Order;
import com.devnous.erp.Exceptions.ResourceNotFoundException;

import java.util.List;

public interface OrderService {
    void createOrder(Order order);

    Order readOrder(int id) throws ResourceNotFoundException;

    List<Order> readAllActiveOrder(int idCompany);

    List<Order> readAllRemovedOrder(int idCompany);

    void updateOrder(Order order) throws ResourceNotFoundException;

    void deleteOrder(int id);

    void softDeleteOrder(int id) throws ResourceNotFoundException;
}
