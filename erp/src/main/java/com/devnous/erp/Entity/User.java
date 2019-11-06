package com.devnous.erp.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="user")
public class User implements Serializable {
    public static final int ELIMINADO  = 0;
    public static final int ACTIVO     = 1;
    public static final int SUSPENDIDO = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @NotNull
    @Column
    private String username;

    @NotNull
    @Column
    private String password;

    @NotNull
    @Column
    private Date create_at;

    @NotNull
    @Column(columnDefinition = "integer default 1")
    private int status;

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    @OneToOne(mappedBy = "user")
    private Staff staff;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
