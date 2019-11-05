package com.devnous.erp.Repository;

import com.devnous.erp.Entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("districtRepository")
public interface DistrictRepository extends JpaRepository<District, Serializable> {

    District findById(int id);
}
