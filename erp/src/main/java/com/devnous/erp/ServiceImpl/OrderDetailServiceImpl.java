package com.devnous.erp.ServiceImpl;

import com.devnous.erp.Entity.OrderDetail;
import com.devnous.erp.Exceptions.ResourceNotFoundException;
import com.devnous.erp.Repository.OrderDetailRepository;
import com.devnous.erp.Service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("orderDetailService")
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    @Qualifier("orderDetailRepository")
    OrderDetailRepository orderDetailRepository;
    private String clase = OrderDetail.class.getSimpleName();

    @Override
    public void createOrderDetail(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail readOrderDetail(int id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id);
        if (orderDetail == null) {
            throw new ResourceNotFoundException(clase);
        }
        return orderDetail;
    }

    @Override
    public List<OrderDetail> readAllOrderDetail() {
        return orderDetailRepository.findAll();
    }

    @Override
    public void updateOrderDetail(OrderDetail orderDetail) {
        if (orderDetailRepository.findById(orderDetail.getId()) != null) {
            orderDetailRepository.save(orderDetail);
        }else{
            throw new ResourceNotFoundException(clase);
        }
    }

    @Override
    public void deleteOrderDetail(int id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public void softDeleteOrderDetail(int id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id);
        if (orderDetail != null) {
            orderDetail.setStatus(0);
            orderDetailRepository.save(orderDetail);
        }else{
            throw new ResourceNotFoundException(clase);
        }
    }
}
