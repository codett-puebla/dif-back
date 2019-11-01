package com.devnous.erp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Comparator;

@Entity
@Table(name = "quotationDetail")
public class QuotationDetail implements Serializable {
    private static final long serialVersionUID = -1026586420973670424L;

    //Sobreescribiendo el metodo de comparator para poder ordernar la lista
    public static Comparator<QuotationDetail> QuotationDetailOrderByIdQuotation = new Comparator<QuotationDetail>() {
        @Override
        public int compare(QuotationDetail o1, QuotationDetail o2) {
            int idQuotation1 = o1.getQuotation().getId();
            int idQuotation2 = o2.getQuotation().getId();

            /*ascending order - we can invert the order*/
            return idQuotation1-idQuotation2;
        }
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    @NotNull
    private int quantity;
    @Column(columnDefinition = "Decimal(15,6)")
    @NotNull
    private Double unitPrice;
    @Column(columnDefinition = "Decimal(15,6)")
    @NotNull
    private Double totalPrice;
    @Column
    @NotNull
    private int status;
    @ManyToOne
    @JoinColumn(name = "idQuotation", referencedColumnName = "id")
    private Quotation quotation;
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

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
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

    @JsonIgnoreProperties({"quotationDetails"})
    public Quotation getQuotation() {
        return quotation;
    }

    public void setQuotation(Quotation quotation) {
        this.quotation = quotation;
    }

    @JsonIgnoreProperties({"inventories"})
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
