package com.devnous.erp.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {

    public static final int ACTIVO = 1;
    public static final int CANCELADO = 0;

    public static final int VENTA = 1;
    public static final int COMPRA = 2;
    public static final int SALIDA = 3;
    public static final int ENTRADA = 4;
    public static final int INVENTARIO = 5;

    public static final String REASON_SALIDA_VENTA = "SALIDA POR VENTA";
    public static final String REASON_SALIDA_INVENTARIO = "SALIDA DE INVENTARIO";
    public static final String REASON_ENTRADA_COMPRA = "ENTRADA POR COMPRA";
    public static final String REASON_ENTRADA_INVENTARIO = "ENTRADA POR INVENTARIO";
    public static final String REASON_ENTRADA_TRANSFERENCIA_ALMACEN = "ENTRADA POR TRANSFERENCIA ALMACEN";
    public static final String REASON_SALIDA_TRANSFERENCIA_ALMACEN = "SALIDA POR TRANSFERENCIA ALMACEN";

    private static final long serialVersionUID = 8067711490570892423L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    @NotNull
    private int quantity;
    @Column
    @NotNull
    private String reason;
    @Column
    @NotNull
    private int status;
    @Column
    @NotNull
    private int movementStatus;
    @Column
    @NotNull
    private int typeTransaction;
    @Column
    @NotNull
    private int idTransaction;

    @ManyToOne
    @JoinColumn(name = "idItem")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "idWarehouse")
    private Warehouse warehouse;


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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public int getMovementStatus() {
        return movementStatus;
    }

    public void setMovementStatus(int movementStatus) {
        this.movementStatus = movementStatus;
    }

    public int getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(int typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public int getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(int idTransaction) {
        this.idTransaction = idTransaction;
    }
}
