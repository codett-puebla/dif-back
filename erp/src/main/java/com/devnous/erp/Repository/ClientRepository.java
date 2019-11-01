package com.devnous.erp.Repository;

import com.devnous.erp.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("clientRepository")
public interface ClientRepository extends JpaRepository<Client, Serializable> {
    Client findById(int id);

    List<Client> findByStatusAndIdCompany(int status, int idCompany);
}
