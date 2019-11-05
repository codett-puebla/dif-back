package com.devnous.erp.Service;

import com.devnous.erp.Entity.Warehouse;
import com.devnous.erp.Exceptions.ResourceNotFoundException;

import java.util.List;

public interface WarehouseService {
    void createWarehouse(Warehouse warehouse);

    Warehouse readWarehouse(int id) throws ResourceNotFoundException;

    List<Warehouse> readAllActiveWarehouse();

    List<Warehouse> readAllRemovedWarehouse();

    void updateWarehouse(Warehouse warehouse) throws ResourceNotFoundException;

    void deleteWarehouse(int id);

    void softDeleteWarehouse(int id) throws ResourceNotFoundException;

    void transferWarehouse(int idWarehouse1,int idWarehouse2,int idItem, int quantity);
}
