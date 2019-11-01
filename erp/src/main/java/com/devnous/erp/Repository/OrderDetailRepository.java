package com.devnous.erp.Repository;

import com.devnous.erp.Entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("orderDetailRepository")
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Serializable> {
    OrderDetail findById(int id);
}
