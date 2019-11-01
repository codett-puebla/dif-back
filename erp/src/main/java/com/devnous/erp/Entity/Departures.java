package com.devnous.erp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "departures")
public class Departures implements Serializable {
    public static final int ACTIVO = 1;
    public static final int ELIMINADO = 0;
    public static final int CANCELADO = 2;
    private static final long serialVersionUID = -6617718968992760812L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private Date date;
    @Column
    @NotNull
    private String series;
    @Column
    @NotNull
    private String folio;
    @Column
    @NotNull
    private int idCompany;
    @Column
    @NotNull
    private int status;

    //relaciones y fk
    @ManyToOne
    @JoinColumn(name = "idStaff", referencedColumnName = "id")
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "idWarehouse", referencedColumnName = "id")
    private Warehouse warehouse;

    @OneToMany(mappedBy = "departures", cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<DepartureDetail> departureDetails;

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

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }


    @JsonIgnoreProperties({"departures"})
    public List<DepartureDetail> getDepartureDetails() {
        return departureDetails;
    }

    public void setDepartureDetails(List<DepartureDetail> departureDetails) {
        this.departureDetails = departureDetails;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @PostPersist
    public void insertCascade() {
        for (DepartureDetail departureDetail : this.getDepartureDetails()) {
            departureDetail.setDepartures(this);
        }
    }
}
