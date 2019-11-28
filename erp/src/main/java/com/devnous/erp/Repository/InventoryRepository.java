package com.devnous.erp.Repository;

import com.devnous.erp.Entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("inventoryRepository")
public interface InventoryRepository extends JpaRepository<Inventory, Serializable>{
    Inventory findById(int id);

    @Query(value = "Select * from inventory as i where i.status <= ?1 ORDER BY i.id", nativeQuery = true)
    List<Inventory> findByStatus(int status);

    List<Inventory> findByItemId(int id);

    List<Inventory> findByItemIdAndWarehouseId(int idItem, int idWarehouse);

    Inventory findTopByItemIdAndWarehouseId(int idItem, int idWarehouse);
}
