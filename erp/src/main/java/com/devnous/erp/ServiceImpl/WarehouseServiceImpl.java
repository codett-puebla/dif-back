package com.devnous.erp.ServiceImpl;

import com.devnous.erp.Entity.Inventory;
import com.devnous.erp.Entity.Transaction;
import com.devnous.erp.Entity.Warehouse;
import com.devnous.erp.Exceptions.ResourceNotFoundException;
import com.devnous.erp.Exceptions.TransactionInterruptedException;
import com.devnous.erp.Repository.InventoryRepository;
import com.devnous.erp.Repository.TransactionRepository;
import com.devnous.erp.Repository.WarehouseRepository;
import com.devnous.erp.Service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("warehouseService")
public class WarehouseServiceImpl implements WarehouseService {
    @Autowired
    @Qualifier("warehouseRepository")
    WarehouseRepository warehouseRepository;
    private String clase = Warehouse.class.getSimpleName();

    @Autowired
    @Qualifier("inventoryRepository")
    InventoryRepository inventoryRepository;

    @Autowired
    @Qualifier("transactionRepository")
    TransactionRepository transactionRepository;

    @Override
    public void createWarehouse(Warehouse warehouse) {
        warehouseRepository.save(warehouse);
    }

    @Override
    public Warehouse readWarehouse(int id) {
        Warehouse warehouse = warehouseRepository.findById(id);
        if (warehouse == null) {
            throw new ResourceNotFoundException(clase);
        }
        return warehouse;
    }

    @Override
    public List<Warehouse> readAllActiveWarehouse(int idCompany) {
        return warehouseRepository.findByStatusAndIdCompany(1, idCompany);
    }

    @Override
    public List<Warehouse> readAllRemovedWarehouse(int idCompany) {
        return warehouseRepository.findByStatusAndIdCompany(0, idCompany);

    }

    @Override
    public void updateWarehouse(Warehouse warehouse) {
        if (warehouseRepository.findById(warehouse.getId()) != null) {
            warehouseRepository.save(warehouse);
        } else {
            throw new ResourceNotFoundException(clase);
        }
    }

    @Override
    public void deleteWarehouse(int id) {
        warehouseRepository.deleteById(id);
    }

    @Override
    public void softDeleteWarehouse(int id) {
        Warehouse warehouse = warehouseRepository.findById(id);
        if (warehouse != null) {
            warehouse.setStatus(0);
            warehouseRepository.save(warehouse);
        } else {
            throw new ResourceNotFoundException(clase);
        }
    }

    @Override
    public void transferWarehouse(int idWarehouse1, int idWarehouse2, int idItem, int quantity) {
        Inventory inventory = inventoryRepository.findTopByItemIdAndWarehouseId(idItem, idWarehouse1);
        Inventory inventory2 = inventoryRepository.findTopByItemIdAndWarehouseId(idItem, idWarehouse2);
        if (inventory != null && inventory2 != null && inventory.getQuantity() >= quantity) {
            inventory.setQuantity(inventory.getQuantity() - quantity); //quitamos del primer inventario
            inventory2.setQuantity(inventory2.getQuantity() + quantity); //añadimos al segundo

            Transaction transaction = new Transaction();
            transaction.setReason(Transaction.REASON_SALIDA_TRANSFERENCIA_ALMACEN);
            transaction.setTypeTransaction(Transaction.INVENTARIO);
            transaction.setQuantity(quantity);
            makeTransaction(inventory, transaction);

            transaction = new Transaction();
            transaction.setReason(Transaction.REASON_ENTRADA_TRANSFERENCIA_ALMACEN);
            transaction.setTypeTransaction(Transaction.INVENTARIO);
            transaction.setQuantity(quantity * -1);
            makeTransaction(inventory2, transaction);

        } else {
            throw new TransactionInterruptedException();
        }
    }

    private void makeTransaction(Inventory inventory, Transaction transaction) {
        transaction.setItem(inventory.getItem());
        transaction.setWarehouse(inventory.getWarehouse());
        transaction.setIdTransaction(inventory.getId());
        transaction.setIdCompany(inventory.getIdCompany());
        transaction.setStatus(Transaction.ACTIVO);
        transaction.setMovementStatus(Transaction.ACTIVO);
        transactionRepository.save(transaction);
    }
}
