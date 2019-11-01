package com.devnous.erp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "salesHeader")
public class SalesHeader implements Serializable {
    private static final long serialVersionUID = 3907315247324012399L;

    public static final int ACTIVE = 1;
    public static final int CANCELADO = 2;
    public static final int ELIMINADO = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    @NotNull
    private Date date;
    @Column
    @NotNull
    private String series;
    @Column
    private String folio;
    @Column(columnDefinition = "Decimal(15,6)")
    private Double subtotal;
    @Column(columnDefinition = "Decimal(15,6)")
    private Double total;
    @Column(columnDefinition = "Decimal(15,6)")
    private Double discount;
    @Column
    @NotNull
    private int idCompany;
    @Column
    @NotNull
    private int status;
    @Column
    @NotNull
    private int idUser;

    //relaciones y fk
    @ManyToOne
    @JoinColumn(name = "idClient", referencedColumnName = "id")
    @NotNull
    private Client client;
    @ManyToOne
    @JoinColumn(name = "idWarehouse", referencedColumnName = "id")
    @NotNull
    private Warehouse warehouse;

    @OneToMany(mappedBy = "salesHeader", cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<SalesDetail> salesDetails;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public List<SalesDetail> getSalesDetails() {
        return salesDetails;
    }

    @JsonIgnoreProperties({"salesHeader"})
    public void setSalesDetails(List<SalesDetail> salesDetails) {
        this.salesDetails = salesDetails;
    }

    @PostPersist
    public void insertCascade() {
        for (SalesDetail salesDetail : this.getSalesDetails()) {
            salesDetail.setSalesHeader(this);
        }
    }
}
