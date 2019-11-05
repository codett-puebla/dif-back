package com.devnous.erp.Repository;

import com.devnous.erp.Entity.SalesHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("saleRepository")
public interface SaleRepository extends JpaRepository<SalesHeader, Serializable> {
    SalesHeader findById(int id);

    List<SalesHeader> findByStatus(int status);

    @Query(value = "Select * from SalesHeader as s where s.folio LIKE  '%-%-%' AND s.series = ?1 ORDER BY s.id DESC LIMIT 1", nativeQuery = true)
    SalesHeader findTopBySeries(String series);

    SalesHeader findByFolioAndSeries(String folio, String series);

    SalesHeader findByIdAndStatus(int id, int status);
}
