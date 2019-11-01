package com.devnous.erp.Service;

import com.devnous.erp.Entity.Entry;
import com.devnous.erp.Exceptions.ResourceNotFoundException;
import com.devnous.erp.Exceptions.UniqueAttributeException;

import java.util.List;

public interface EntryService {
    void createEntry(Entry entry) throws UniqueAttributeException;

    Entry readEntrys(int id) throws ResourceNotFoundException;

    List<Entry> readAllActiveEntrys(int idCompany);

    List<Entry> readAllRemovedEntrys(int idCompany);

    void updateEntrys(Entry entry) throws ResourceNotFoundException;

    void deleteEntrys(int id);

    void softDeleteEntry(int id) throws ResourceNotFoundException;
}
