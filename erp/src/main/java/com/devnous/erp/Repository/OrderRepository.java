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

    List<Order> findByStatusAndIdCompany(int status, int idCompany);

    @Query(value = "Select * from deverp.Order as o where o.folio LIKE  '%-%-%' AND o.series = ?1 AND o.idCompany = ?2 ORDER BY o.id DESC LIMIT 1", nativeQuery = true)
    Order findTopBySeriesAndIdCompany(String series, int idCompany);

    Order findByFolioAndSeriesAndIdCompany(String folio, String series, int idCompany);
}
