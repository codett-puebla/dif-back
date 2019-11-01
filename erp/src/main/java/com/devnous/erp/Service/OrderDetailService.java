package com.devnous.erp.Service;

import com.devnous.erp.Entity.OrderDetail;
import com.devnous.erp.Exceptions.ResourceNotFoundException;

import java.util.List;

public interface OrderDetailService {
    void createOrderDetail(OrderDetail orderDetail);

    OrderDetail readOrderDetail(int id) throws ResourceNotFoundException;

    List<OrderDetail> readAllOrderDetail();

    void updateOrderDetail(OrderDetail orderDetail) throws ResourceNotFoundException;

    void deleteOrderDetail(int id);

    void softDeleteOrderDetail(int id) throws ResourceNotFoundException;
}
