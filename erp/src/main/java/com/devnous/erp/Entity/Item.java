package com.devnous.erp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "item")
public class Item implements Serializable {
    private static final long serialVersionUID = 9208735564834029856L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    @NotNull
    private String code;
    @Column
    private String description;
    @Column
    private Integer unitMeasureSale;
    @Column
    private Integer unitMeasurePurchase;
    @Column
    private Integer idKeySAT;
    @Column
    private Integer idUnitMeasureSAT;
    @Column
    private String image; //path de la imagen
    @Column
    private String line;
    @Column
    private String trademark;
    @Column(columnDefinition = "boolean")
    private boolean billable;
    @Column(columnDefinition = "boolean")
    private boolean storable;
    @Column
    private int purchaseAmount;
    @Column
    private int saleAmount;
    @Column
    @NotNull
    private int status;

    @OneToMany(mappedBy = "item", cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Inventory> inventories;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUnitMeasureSale() {
        return unitMeasureSale;
    }

    public void setUnitMeasureSale(Integer unitMeasureSale) {
        this.unitMeasureSale = unitMeasureSale;
    }

    public Integer getUnitMeasurePurchase() {
        return unitMeasurePurchase;
    }

    public void setUnitMeasurePurchase(Integer unitMeasurePurchase) {
        this.unitMeasurePurchase = unitMeasurePurchase;
    }

    public Integer getIdKeySAT() {
        return idKeySAT;
    }

    public void setIdKeySAT(Integer idKeySAT) {
        this.idKeySAT = idKeySAT;
    }

    public Integer getIdUnitMeasureSAT() {
        return idUnitMeasureSAT;
    }

    public void setIdUnitMeasureSAT(Integer idUnitMeasureSAT) {
        this.idUnitMeasureSAT = idUnitMeasureSAT;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getTrademark() {
        return trademark;
    }

    public void setTrademark(String trademark) {
        this.trademark = trademark;
    }

    public boolean isBillable() {
        return billable;
    }

    public void setBillable(boolean billable) {
        this.billable = billable;
    }

    public boolean isStorable() {
        return storable;
    }

    public void setStorable(boolean storable) {
        this.storable = storable;
    }

    public int getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(int purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public int getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(int saleAmount) {
        this.saleAmount = saleAmount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @JsonIgnoreProperties({"item"})
    public List<Inventory> getInventories() {
        return inventories;
    }

    public void setInventories(List<Inventory> inventories) {
        this.inventories = inventories;
    }

    @PostPersist
    public void insertCascade(){
        for (Inventory inventory: this.getInventories()) {
            inventory.setItem(this);
        }
    }
}
