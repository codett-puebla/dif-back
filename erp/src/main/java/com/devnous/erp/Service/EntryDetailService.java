package com.devnous.erp.Service;

import com.devnous.erp.Entity.EntryDetail;
import com.devnous.erp.Exceptions.ResourceNotFoundException;

import java.util.List;

public interface EntryDetailService {
    void createEntryDetail(EntryDetail entryDetail);

    EntryDetail readEntryDetail(int id) throws ResourceNotFoundException;

    List<EntryDetail> readAllEntryDetail();

    void updateEntryDetail(EntryDetail entryDetail) throws ResourceNotFoundException;

    void deleteEntryDetail(int id);

    void softDeleteEntryDetail(int id) throws ResourceNotFoundException;
}
