package com.devnous.erp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "salesDetail")
public class SalesDetail implements Serializable {
    private static final long serialVersionUID = -1026586420973670424L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private int quantity;
    @Column(columnDefinition = "Decimal(15,6)")
    private Double unitPrice;
    @Column(columnDefinition = "Decimal(15,6)")
    private Double unitImport;
    @Column(columnDefinition = "Decimal(15,6)")
    private Double totalPrice;
    @Column(columnDefinition = "Decimal(15,6)")
    private Double totalImport;
    @Column(columnDefinition = "Decimal(15,6)")
    private Double discount;
    @Column
    private Integer currentAmount;
    @Column
    @NotNull
    private int status;

    //relaciones y fk
    @ManyToOne
    @JoinColumn(name = "idSalesHeader", referencedColumnName = "id")
    private SalesHeader salesHeader;
    @ManyToOne
    @JoinColumn(name = "idItem", referencedColumnName = "id")
    private Item item;

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

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getUnitImport() {
        return unitImport;
    }

    public void setUnitImport(Double unitImport) {
        this.unitImport = unitImport;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getTotalImport() {
        return totalImport;
    }

    public void setTotalImport(Double totalImport) {
        this.totalImport = totalImport;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Integer currentAmount) {
        this.currentAmount = currentAmount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @JsonIgnoreProperties({"salesDetails"})
    public SalesHeader getSalesHeader() {
        return salesHeader;
    }

    public void setSalesHeader(SalesHeader salesHeader) {
        this.salesHeader = salesHeader;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
