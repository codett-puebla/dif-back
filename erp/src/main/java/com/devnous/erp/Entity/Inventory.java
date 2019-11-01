package com.devnous.erp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "inventory")
public class Inventory implements Serializable {
    private static final long serialVersionUID = 8067711490570892423L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    @NotNull
    private int quantity;
    @Column
    @NotNull
    private int status;
    @Column
    @NotNull
    private int idCompany;
    @ManyToOne
    @JoinColumn(name = "idItem", referencedColumnName = "id")
    @NotNull
    private Item item;

    @ManyToOne
    @JoinColumn(name = "idWarehouse", referencedColumnName = "id")
    @NotNull
    private Warehouse warehouse;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @JsonIgnoreProperties({"inventories"})
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public int getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }
}
