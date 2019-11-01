package com.devnous.erp.Repository;

import com.devnous.erp.Entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("inventoryRepository")
public interface InventoryRepository extends JpaRepository<Inventory, Serializable>{
    Inventory findById(int id);

    List<Inventory> findByStatusAndIdCompany(int status, int idCompany);

    List<Inventory> findByItemId(int id);

    List<Inventory> findByItemIdAndWarehouseId(int idItem, int idWarehouse);

    Inventory findTopByItemIdAndWarehouseId(int idItem, int idWarehouse);
}
