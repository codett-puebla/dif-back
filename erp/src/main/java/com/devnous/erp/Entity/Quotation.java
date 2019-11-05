package com.devnous.erp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "quotation")
public class Quotation implements Serializable {
    private static final long serialVersionUID = -1026586420973670424L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    @NotNull
    private String series;
    @NotNull
    @Column
    private String folio;
    @NotNull
    @Column
    private Date date;
    @Column(columnDefinition = "Decimal(15,6)")
    @NotNull
    private Double totalPrice;
    @Column
    @NotNull
    private int status;
    @Column
    private int idUser;

    //relaciones y fk
    @ManyToOne
    @JoinColumn(name = "idOrder", referencedColumnName = "id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "idProvider", referencedColumnName = "id")
    private Provider provider;

    @OneToMany(mappedBy = "quotation", cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<QuotationDetail> quotationDetails;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    @JsonIgnoreProperties({"quotation"})
    public List<QuotationDetail> getQuotationDetails() {
        return quotationDetails;
    }

    public void setQuotationDetails(List<QuotationDetail> quotationDetails) {
        this.quotationDetails = quotationDetails;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @PostPersist
    public void insertCascade() {
        for (QuotationDetail quotationDetail : this.getQuotationDetails()) {
            quotationDetail.setQuotation(this);
        }
    }
}
