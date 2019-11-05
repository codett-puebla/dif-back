package com.devnous.erp.Repository;

import com.devnous.erp.Entity.PurchaseHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("purchaseRepository")
public interface PurchaseRepository extends JpaRepository<PurchaseHeader, Serializable> {
    PurchaseHeader findById(int id);

    List<PurchaseHeader> findByStatus(int status);

    PurchaseHeader findByIdAndStatus(int id, int status);

    @Query(value = "Select * from PurchaseHeader as p where p.folio LIKE  '%-%-%' AND p.series = ?1 ORDER BY p.id DESC LIMIT 1", nativeQuery = true)
    PurchaseHeader findTopBySeries(String series);

    PurchaseHeader findByFolioAndSeries(String folio, String series);
}
