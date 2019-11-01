package com.devnous.erp.Repository;

import com.devnous.erp.Entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("providerRepository")
public interface ProviderRepository extends JpaRepository<Provider, Serializable> {
    Provider findById(int id);

    List<Provider> findByStatusAndIdCompany(int status, int idCompany);
}
