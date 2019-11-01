package com.devnous.erp.Service;

import com.devnous.erp.Entity.Client;
import com.devnous.erp.Exceptions.ResourceNotFoundException;

import java.util.List;

public interface ClientService {
    void createClient(Client client);

    Client readClient(int id) throws ResourceNotFoundException;

    List<Client> readAllActiveClient(int idCompany);

    List<Client> readAllRemovedClient(int idCompany);

    void updateClient(Client client) throws ResourceNotFoundException;

    void deleteClient(int id);

    void softDeleteClient(int id) throws ResourceNotFoundException;
}
