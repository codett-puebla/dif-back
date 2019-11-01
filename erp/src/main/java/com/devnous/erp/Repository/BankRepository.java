package com.devnous.erp.Repository;

import com.devnous.erp.Entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("bankRepository")
public interface BankRepository extends JpaRepository<Bank, Serializable> {
    Bank findById(int id);

    List<Bank> findByStatusAndIdCompany(int status, int idCompany);
}
