package com.devnous.erp.ServiceImpl;

import com.devnous.erp.Entity.Provider;
import com.devnous.erp.Exceptions.ResourceNotFoundException;
import com.devnous.erp.Repository.ProviderRepository;
import com.devnous.erp.Service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("providerService")
public class ProviderServiceImpl implements ProviderService {
    @Autowired
    @Qualifier("providerRepository")
    ProviderRepository providerRepository;
    private String clase = Provider.class.getSimpleName();

    @Override
    public void createProvider(Provider provider) {
        providerRepository.save(provider);
    }

    @Override
    public Provider readProvider(int id) {
        Provider provider = providerRepository.findById(id);
        if(provider ==null) {
            throw new ResourceNotFoundException(clase);
        }
        return provider;
    }

    @Override
    public List<Provider> readAllActiveProvider(int idCompany) {
        return providerRepository.findByStatusAndIdCompany(1, idCompany);
    }

    @Override
    public List<Provider> readAllRemovedProvider(int idCompany) {
        return providerRepository.findByStatusAndIdCompany(0, idCompany);

    }

    @Override
    public void updateProvider(Provider provider) {
        if (providerRepository.findById(provider.getId()) != null) {
            providerRepository.save(provider);
        }else{
            throw new ResourceNotFoundException(clase);
        }
    }

    @Override
    public void deleteProvider(int id) {
        providerRepository.deleteById(id);
    }

    @Override
    public void softDeleteProvider(int id) {
        Provider provider = providerRepository.findById(id);
        if (provider != null) {
            provider.setStatus(0);
            providerRepository.save(provider);
        }else{
            throw new ResourceNotFoundException(clase);
        }
    }
}
