package com.devnous.erp.Repository;

import com.devnous.erp.Entity.SalesDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("salesDetailRepository")
public interface SalesDetailRepository extends JpaRepository<SalesDetail, Serializable> {
    SalesDetail findById(int id);
}
