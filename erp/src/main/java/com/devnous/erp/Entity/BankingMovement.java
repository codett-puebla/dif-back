package com.devnous.erp.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "bankingmovement")
public class BankingMovement implements Serializable {
    private static final long serialVersionUID = -7456382372098106874L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    @NotNull
    private String reference;
    @Column(columnDefinition = "DECIMAL(16,5)")
    @NotNull
    private Double amount;
    @Column
    @NotNull
    private int movementStatus;
    @Column
    @NotNull
    private int idCompany;
    @Column
    @NotNull
    private int status;
    @ManyToOne
    @JoinColumn(name = "idBank", referencedColumnName = "id")
    @NotNull
    private Bank bank;
    @ManyToOne
    @JoinColumn(name = "idPurchaseHeader", referencedColumnName = "id")
    private PurchaseHeader purchaseHeader;
    @ManyToOne
    @JoinColumn(name = "idSalesHeader", referencedColumnName = "id")
    private SalesHeader salesHeader;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getMovementStatus() {
        return movementStatus;
    }

    public void setMovementStatus(int movementStatus) {
        this.movementStatus = movementStatus;
    }

    public int getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public PurchaseHeader getPurchaseHeader() {
        return purchaseHeader;
    }

    public void setPurchaseHeader(PurchaseHeader purchaseHeader) {
        this.purchaseHeader = purchaseHeader;
    }

    public SalesHeader getSalesHeader() {
        return salesHeader;
    }

    public void setSalesHeader(SalesHeader salesHeader) {
        this.salesHeader = salesHeader;
    }
}
