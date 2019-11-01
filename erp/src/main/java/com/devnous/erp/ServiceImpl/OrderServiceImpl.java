package com.devnous.erp.ServiceImpl;

import com.devnous.erp.Entity.Order;
import com.devnous.erp.Entity.OrderDetail;
import com.devnous.erp.Exceptions.ResourceNotFoundException;
import com.devnous.erp.Exceptions.UniqueAttributeException;
import com.devnous.erp.Repository.OrderRepository;
import com.devnous.erp.Service.HelperFolioService;
import com.devnous.erp.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    @Qualifier("orderRepository")
    OrderRepository orderRepository;
    private String clase = Order.class.getSimpleName();

    @Autowired
    @Qualifier("helperFolioService")
    private HelperFolioService helperFolioService;

    @Override
    @SuppressWarnings("Duplicates")
    public void createOrder(Order order) {
        if (order.getFolio() == null || order.getFolio().isEmpty()) {
            String lastFolio = ""; //we need to obtain the last folio of a series.
            try {
                lastFolio = orderRepository.findTopBySeriesAndIdCompany(order.getSeries(), order.getIdCompany()).getFolio();
            } catch (Exception e) {
                //nothing to do
                System.out.println(e);
            }
            order.setFolio(helperFolioService.createNewFolio(lastFolio));
        }
        Order exist = orderRepository.findByFolioAndSeriesAndIdCompany(order.getFolio(), order.getSeries(), order.getIdCompany());
        if (exist == null) {
            order.setDate(new Date(System.currentTimeMillis()));
            orderRepository.save(order);
        } else {
            throw new UniqueAttributeException("[series,folios,idCompany]");
        }
    }

    @Override
    public Order readOrder(int id) {
        Order order = orderRepository.findById(id);
        if (order == null) {
            throw new ResourceNotFoundException(clase);
        }
        return order;
    }

    @Override
    public List<Order> readAllActiveOrder(int idCompany) {
        return orderRepository.findByStatusAndIdCompany(1, idCompany);
    }

    @Override
    public List<Order> readAllRemovedOrder(int idCompany) {
        return orderRepository.findByStatusAndIdCompany(0, idCompany);
    }

    @Override
    public void updateOrder(Order order) {
        if (orderRepository.findById(order.getId()) != null) {
            orderRepository.save(order);
        } else {
            throw new ResourceNotFoundException(clase);
        }
    }

    @Override
    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void softDeleteOrder(int id) {
        Order order = orderRepository.findById(id);
        if (order != null) {
            order.setStatus(0);
            for (OrderDetail orderDetail: order.getOrderDetails()) {
                orderDetail.setStatus(0);
            }
            orderRepository.save(order);
        } else {
            throw new ResourceNotFoundException(clase);
        }
    }
}
