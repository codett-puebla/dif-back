package com.devnous.erp.Repository;

import com.devnous.erp.Entity.PurchaseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("purchaseDetailRepository")
public interface PurchaseDetailRepository extends JpaRepository<PurchaseDetail, Serializable> {
    PurchaseDetail findById(int id);
}
