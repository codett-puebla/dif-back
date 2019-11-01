package com.devnous.erp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "entrydetail")
public class EntryDetail implements Serializable {
    private static final long serialVersionUID = 9208735564834029856L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    @NotNull
    private int quantity;
    @Column
    @NotNull
    private int status;

    @ManyToOne
    @JoinColumn(name = "idEntry", referencedColumnName = "id")
    @NotNull
    private Entry entry;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @JsonIgnoreProperties({"entryDetails"})
    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
