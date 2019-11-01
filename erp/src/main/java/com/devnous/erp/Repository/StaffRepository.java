package com.devnous.erp.Repository;

import com.devnous.erp.Entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("staffRepository")
public interface StaffRepository extends JpaRepository<Staff, Serializable> {
    Staff findById(int id);

    List<Staff> findByStatusAndIdCompany(int status, int idCompany);
}
