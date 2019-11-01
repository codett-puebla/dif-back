package com.devnous.erp.Repository;

import com.devnous.erp.Entity.BankingMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("bankingMovementRepository")
public interface BankingMovementRepository extends JpaRepository<BankingMovement, Serializable>{
    BankingMovement findById(int id);

    List<BankingMovement> findByStatusAndIdCompany(int status, int idCompany);

}
