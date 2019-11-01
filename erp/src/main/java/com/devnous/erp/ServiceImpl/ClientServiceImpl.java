package com.devnous.erp.ServiceImpl;

import com.devnous.erp.Exceptions.ResourceNotFoundException;
import com.devnous.erp.Repository.ClientRepository;
import com.devnous.erp.Service.ClientService;
import com.devnous.erp.Entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("clientService")
public class ClientServiceImpl implements ClientService {
    @Autowired
    @Qualifier("clientRepository")
    ClientRepository clientRepository;
    private String clase = Client.class.getSimpleName();

    @Override
    public void createClient(Client client) {
        clientRepository.save(client);
    }

    @Override
    public Client readClient(int id) throws ResourceNotFoundException {
        Client client = clientRepository.findById(id);
        if(client == null){
            throw new ResourceNotFoundException(clase);
        }
        return client;
    }

    @Override
    public List<Client> readAllActiveClient(int idCompany) {
        return clientRepository.findByStatusAndIdCompany(1,idCompany);
    }

    @Override
    public List<Client> readAllRemovedClient(int idCompany) {
        return clientRepository.findByStatusAndIdCompany(0,idCompany);
    }

    @Override
    public void updateClient(Client client) {
        if (clientRepository.findById(client.getId()) != null) {
            clientRepository.save(client);
        }else{
                throw new ResourceNotFoundException(clase);
        }
    }

    @Override
    public void deleteClient(int id) {
        clientRepository.deleteById(id);
    }

    @Override
    public void softDeleteClient(int id) {
        Client client = clientRepository.findById(id);
        if (client != null) {
            client.setStatus(0);
            clientRepository.save(client);
        }else{
            throw new ResourceNotFoundException(clase);
        }
    }
}
