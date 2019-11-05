package com.devnous.erp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "purchaseHeader")
public class PurchaseHeader implements Serializable {

    public static final int ACTIVO = 1;
    public static final int ELIMNADO = 0;
    public static final int CANCELADO = 2;
    private static final long serialVersionUID = 5380375823057185047L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private Date date;
    @Column(columnDefinition = "Decimal(15,6)")
    @NotNull
    private Double total;
    @Column(columnDefinition = "Decimal(15,6)")
    private Double discount;
    @Column
    private String series;
    @Column
    private String folio;
    @Column
    private Integer idXML;
    @Column
    @NotNull
    private int status;
    @Column
    @NotNull
    private int idUser;
    @ManyToOne
    @JoinColumn(name = "idWarehouse", referencedColumnName = "id")
    @NotNull
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "idStaff", referencedColumnName = "id")
    @NotNull
    private Staff staff;

    @OneToMany(mappedBy = "purchaseHeader", cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<PurchaseDetail> purchaseDetails;

    @ManyToOne
    @JoinColumn(name = "idProvider", referencedColumnName = "id")
    @NotNull
    private Provider provider;


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

    public Integer getIdXML() {
        return idXML;
    }

    public void setIdXML(Integer idXML) {
        this.idXML = idXML;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    @JsonIgnoreProperties({"purchaseHeader"})
    public List<PurchaseDetail> getPurchaseDetails() {
        return purchaseDetails;
    }

    public void setPurchaseDetails(List<PurchaseDetail> purchaseDetails) {
        this.purchaseDetails = purchaseDetails;
    }

    @PostPersist
    public void insertCascade() {
        for (PurchaseDetail purchaseDetail : this.getPurchaseDetails()) {
            purchaseDetail.setPurchaseHeader(this);
        }
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
