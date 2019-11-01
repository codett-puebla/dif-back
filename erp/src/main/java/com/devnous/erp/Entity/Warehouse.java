package com.devnous.erp.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "warehouse")
public class Warehouse implements Serializable {
    private static final long serialVersionUID = 6146678570107404046L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    @NotNull
    private String name;
    @Column
    private String address;
    @Column
    private String reason;
    @Column
    private Integer accountingAccount;
    @Column
    @NotNull
    private int idCompany;
    @Column
    @NotNull
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getAccountingAccount() {
        return accountingAccount;
    }

    public void setAccountingAccount(Integer accountingAccount) {
        this.accountingAccount = accountingAccount;
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
}
