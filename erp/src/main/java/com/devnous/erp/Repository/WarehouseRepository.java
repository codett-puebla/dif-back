package com.devnous.erp.Repository;

import com.devnous.erp.Entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("warehouseRepository")
public interface WarehouseRepository extends JpaRepository<Warehouse, Serializable> {
    Warehouse findById(int id);

    List<Warehouse> findByStatus(int status);
}
