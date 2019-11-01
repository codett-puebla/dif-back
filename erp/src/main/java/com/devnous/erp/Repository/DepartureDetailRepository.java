package com.devnous.erp.Repository;

import com.devnous.erp.Entity.DepartureDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("departureDetailRepository")
public interface DepartureDetailRepository extends JpaRepository<DepartureDetail, Serializable> {
    DepartureDetail findById(int id);
}
