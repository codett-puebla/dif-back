package com.devnous.erp.Repository;

import com.devnous.erp.Entity.Quotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("quotationRepository")
public interface QuotationRepository extends JpaRepository<Quotation, Serializable> {
    Quotation findById(int id);

    List<Quotation> findByStatus(int status);

    @Query(value = "Select * from Quotation as q where q.folio LIKE  '%-%-%' AND q.series = ?1 ORDER BY q.id DESC LIMIT 1", nativeQuery = true)
    Quotation findTopBySeries(String series);

    Quotation findByFolioAndSeries(String folio, String series);

    Quotation findByIdAndStatus(int id, int status);
}
