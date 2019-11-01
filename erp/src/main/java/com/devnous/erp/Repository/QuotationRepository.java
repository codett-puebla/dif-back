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

    List<Quotation> findByStatusAndIdCompany(int status, int idCompany);

    @Query(value = "Select * from Quotation as q where q.folio LIKE  '%-%-%' AND q.series = ?1 AND q.idCompany = ?2 ORDER BY q.id DESC LIMIT 1", nativeQuery = true)
    Quotation findTopBySeriesAndIdCompany(String series, int idCompany);

    Quotation findByFolioAndSeriesAndIdCompany(String folio, String series, int idCompany);

    Quotation findByIdAndStatus(int id, int status);
}
