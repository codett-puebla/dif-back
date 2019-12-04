package com.devnous.erp.Service;

import com.devnous.erp.Entity.Departures;
import com.devnous.erp.Exceptions.ResourceNotFoundException;

import java.util.List;

public interface DeparturesService {
    void createDeparture(Departures departures);

    Departures readDepartures(int id) throws ResourceNotFoundException;

    Departures verifyFolio(String folio);

    Boolean verifyQuantity(Integer quantity, Integer idItem);

    List<Departures> readAllActiveDepartures();

    List<Departures> readAllRemovedDepartures();

    void updateDepartures(Departures departures) throws ResourceNotFoundException;

    void deleteDepartures(int id);

    void softDeleteDeparture(int id) throws ResourceNotFoundException;
}
