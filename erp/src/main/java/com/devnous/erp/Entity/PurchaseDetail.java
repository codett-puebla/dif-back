package com.devnous.erp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "purchaseDetail")
public class PurchaseDetail implements Serializable {
    private static final long serialVersionUID = 5380375823057185047L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column(columnDefinition = "Decimal(15,6)")
    private Double unitPrice;
    @Column
    @NotNull
    private Integer quantity;
    @Column(columnDefinition = "Decimal(15,6)")
    @NotNull
    private Double totalPrice;
    @Column
    @NotNull
    private int currentAmount;
    @Column
    @NotNull
    private int status;
    @ManyToOne
    @JoinColumn(name = "idItem", referencedColumnName = "id")
    @NotNull
    private Item item;
    @ManyToOne
    @JoinColumn(name = "idPurchaseHeader", referencedColumnName = "id")
    @NotNull
    private PurchaseHeader purchaseHeader;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @JsonIgnoreProperties({"purchaseDetails"})
    public PurchaseHeader getPurchaseHeader() {
        return purchaseHeader;
    }

    public void setPurchaseHeader(PurchaseHeader purchaseHeader) {
        this.purchaseHeader = purchaseHeader;
    }
}
