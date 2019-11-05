package com.devnous.erp.Service;

import com.devnous.erp.Entity.Inventory;
import com.devnous.erp.Exceptions.ResourceNotFoundException;

import java.util.List;

public interface InventoryService {
    void createInventory(Inventory inventory);

    Inventory readInventory(int id) throws ResourceNotFoundException;

    List<Inventory> readAllActiveInventory();

    List<Inventory> readAllRemovedInventory();

    void updateInventory(Inventory inventory) throws ResourceNotFoundException;

    void deleteInventory(int id);

    void softDeleteInventory(int id) throws ResourceNotFoundException;
}
