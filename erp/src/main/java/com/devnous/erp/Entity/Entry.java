package com.devnous.erp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "entry")
public class Entry implements Serializable {
    public static final int ACTIVO = 1;
    public static final int ELIMINADO = 0;
    public static final int CANCELADO = 2;
    private static final long serialVersionUID = 9208735564834029856L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    @NotNull
    private Date date;
    @Column
    private String series;
    @Column
    private String folio;
    @Column
    @NotNull
    private int idUser;
    @Column
    @NotNull
    private int status;

    @ManyToOne
    @JoinColumn(name = "idWarehouse", referencedColumnName = "id")
    @NotNull
    private Warehouse warehouse;

    @OneToMany(mappedBy = "entry", cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<EntryDetail> entryDetails;

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

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @JsonIgnoreProperties({"entry"})
    public List<EntryDetail> getEntryDetails() {
        return entryDetails;
    }

    public void setEntryDetails(List<EntryDetail> entryDetails) {
        this.entryDetails = entryDetails;
    }

    @PostPersist
    public void insertCascade() {
        for (EntryDetail entryDetail : this.getEntryDetails()) {
            entryDetail.setEntry(this);
        }
    }
}
