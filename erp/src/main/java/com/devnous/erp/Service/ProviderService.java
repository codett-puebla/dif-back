package com.devnous.erp.Service;

import com.devnous.erp.Entity.Provider;
import com.devnous.erp.Exceptions.ResourceNotFoundException;

import java.util.List;

public interface ProviderService {
    void createProvider(Provider provider);

    Provider readProvider(int id) throws ResourceNotFoundException;

    List<Provider> readAllActiveProvider(int idCompany);

    List<Provider> readAllRemovedProvider(int idCompany);

    void updateProvider(Provider provider) throws ResourceNotFoundException;

    void deleteProvider(int id);

    void softDeleteProvider(int id) throws ResourceNotFoundException;
}
