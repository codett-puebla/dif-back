package com.devnous.erp.Repository;

import com.devnous.erp.Entity.QuotationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("quotationDetailRepository")
public interface QuotationDetailRepository extends JpaRepository<QuotationDetail, Serializable> {
    QuotationDetail findById(int id);
}
