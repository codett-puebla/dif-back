package com.devnous.erp.ServiceImpl;

import com.devnous.erp.Entity.Inventory;
import com.devnous.erp.Entity.Item;
import com.devnous.erp.Exceptions.ResourceNotFoundException;
import com.devnous.erp.Repository.InventoryRepository;
import com.devnous.erp.Repository.ItemRepository;
import com.devnous.erp.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("itemService")
public class ItemServiceImpl implements ItemService {
    @Autowired
    @Qualifier("itemRepository")
    ItemRepository itemRepository;
    private String clase = Item.class.getSimpleName();

    @Autowired
    @Qualifier("inventoryRepository")
    InventoryRepository inventoryRepository;

    @Override
    public void createItem(Item item) {
        itemRepository.save(item);
    }

    @Override
    public Item readItem(int id) {
        Item item = itemRepository.findById(id);
        if (item == null) {
            throw new ResourceNotFoundException(clase);
        }
        return item;
    }

    @Override
    public List<Item> readAllActiveItem() {
        return itemRepository.findByStatus(1);
    }

    @Override
    public List<Item> readAllRemovedItem() {
        return itemRepository.findByStatus(0);

    }

    @Override
    public void updateItem(Item item) {
        if (itemRepository.findById(item.getId()) != null) {
            itemRepository.save(item);
        } else {
            throw new ResourceNotFoundException(clase);
        }
    }

    @Override
    public void deleteItem(int id) {
        itemRepository.deleteById(id);
    }

    @Override
    public void softDeleteItem(int id) {
        Item item = itemRepository.findById(id);
        if (item != null) {
            item.setStatus(0);
            itemRepository.save(item);
        } else {
            throw new ResourceNotFoundException(clase);
        }
    }

    @Override
    public List<Item> getItemsByWarehouse(int idWarehouse) {
        return itemRepository.findByStatusAndInventoriesWarehouseId(1, idWarehouse);
    }

    @Override //validate is the quantity is enough
    public boolean isAvailableQuantity(int idItem, int quantity) {
        List<Inventory> inventories = inventoryRepository.findByItemId(idItem);
        int total = 0;
        for (Inventory inventory : inventories) {
            total += inventory.getQuantity();
        }
        return total >= quantity;
    }
}
