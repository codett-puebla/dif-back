package com.devnous.erp.ServiceImpl;

import com.devnous.erp.Entity.Inventory;
import com.devnous.erp.Exceptions.ResourceNotFoundException;
import com.devnous.erp.Repository.InventoryRepository;
import com.devnous.erp.Service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("inventoryService")
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    @Qualifier("inventoryRepository")
    private InventoryRepository inventoryRepository;
    private String clase = Inventory.class.getSimpleName();

    @Override
    public void createInventory(Inventory inventory) {
        inventoryRepository.save(inventory);
    }

    @Override
    public Inventory readInventory(int id) {
        Inventory inventory = inventoryRepository.findById(id);
        if(inventory == null){
            throw new ResourceNotFoundException(clase);
        }
        return inventory;
    }

    @Override
    public List<Inventory> readAllActiveInventory() {
        return inventoryRepository.findByStatus(1);
    }

    @Override
    public List<Inventory> readAllRemovedInventory() {
        return inventoryRepository.findByStatus(0);
    }


    @Override
    public void updateInventory(Inventory inventory) {
        if (inventoryRepository.findById(inventory.getId()) != null) {
            inventoryRepository.save(inventory);
        }else{
            throw new ResourceNotFoundException(clase);
        }
    }

    @Override
    public void deleteInventory(int id) {
        inventoryRepository.deleteById(id);
    }

    @Override
    public void softDeleteInventory(int id) {
        Inventory inventory = inventoryRepository.findById(id);
        if (inventory != null) {
            inventory.setStatus(0);
            inventoryRepository.save(inventory);
        }else{
            throw new ResourceNotFoundException(clase);
        }
    }
}
