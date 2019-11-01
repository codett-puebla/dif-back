package com.devnous.erp.Repository;

import com.devnous.erp.Entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("itemRepository")
public interface ItemRepository extends JpaRepository<Item, Serializable> {
    Item findById(int id);

    List<Item> findByStatusAndIdCompany(int status, int idCompany);

    List<Item> findByStatusAndIdCompanyAndInventoriesWarehouseId(int status, int idCompany, int idWarehouse);
}
