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

    List<SalesHeader> findByStatusAndIdCompany(int status, int idCompany);

    @Query(value = "Select * from SalesHeader as s where s.folio LIKE  '%-%-%' AND s.series = ?1 AND s.idCompany = ?2 ORDER BY s.id DESC LIMIT 1", nativeQuery = true)
    SalesHeader findTopBySeriesAndIdCompany(String series, int idCompany);

    SalesHeader findByFolioAndSeriesAndIdCompany(String folio, String series, int idCompany);

    SalesHeader findByIdAndStatus(int id, int status);
}
