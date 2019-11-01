package com.devnous.erp.Service;

import com.devnous.erp.Entity.Item;
import com.devnous.erp.Exceptions.ResourceNotFoundException;

import java.util.List;

public interface ItemService {
    void createItem(Item item);

    Item readItem(int id) throws ResourceNotFoundException;

    List<Item> readAllActiveItem(int idCompany);

    List<Item> readAllRemovedItem(int idCompany);

    void updateItem(Item item) throws ResourceNotFoundException;

    void deleteItem(int id);

    void softDeleteItem(int id) throws ResourceNotFoundException;

    boolean isAvailableQuantity(int idItem, int quantity);

    List<Item> getItemsByWarehouse(int idCompany, int idWarehouse);
}
