package com.devnous.erp.Repository;

import com.devnous.erp.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("orderRepository")
public interface OrderRepository extends JpaRepository<Order, Serializable> {
    Order findById(int id);

    List<Order> findByStatus(int status);

    @Query(value = "Select * from deverp.Order as o where o.folio LIKE  '%-%-%' AND o.series = ?1 ORDER BY o.id DESC LIMIT 1", nativeQuery = true)
    Order findTopBySeries(String series);

    Order findByFolioAndSeries(String folio, String series);
}
