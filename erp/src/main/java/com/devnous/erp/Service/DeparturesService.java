package com.devnous.erp.Service;

import com.devnous.erp.Entity.Departures;
import com.devnous.erp.Exceptions.ResourceNotFoundException;

import java.util.List;

public interface DeparturesService {
    void createDeparture(Departures departures);

    Departures readDepartures(int id) throws ResourceNotFoundException;

    List<Departures> readAllActiveDepartures(int idCompany);

    List<Departures> readAllRemovedDepartures(int idCompany);

    void updateDepartures(Departures departures) throws ResourceNotFoundException;

    void deleteDepartures(int id);

    void softDeleteDeparture(int id) throws ResourceNotFoundException;
}
