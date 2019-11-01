package com.devnous.erp.ServiceImpl;

import com.devnous.erp.Entity.EntryDetail;
import com.devnous.erp.Exceptions.ResourceNotFoundException;
import com.devnous.erp.Repository.EntryDetailRepository;
import com.devnous.erp.Service.EntryDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("entryDetailService")
public class EntryDetailServiceImpl implements EntryDetailService {
    @Autowired
    @Qualifier("entryDetailRepository")
    EntryDetailRepository entryDetailRepository;
    private String clase = EntryDetail.class.getSimpleName();

    @Override
    public void createEntryDetail(EntryDetail entryDetail) {
        entryDetailRepository.save(entryDetail);
    }

    @Override
    public EntryDetail readEntryDetail(int id) throws ResourceNotFoundException {
        EntryDetail entryDetail = entryDetailRepository.findById(id);
        if(entryDetail ==null){
            throw new ResourceNotFoundException(clase);
        }
        return null;
    }

    @Override
    public List<EntryDetail> readAllEntryDetail() {
        return entryDetailRepository.findAll();
    }

    @Override
    public void updateEntryDetail(EntryDetail entryDetail) throws ResourceNotFoundException {
        if (entryDetailRepository.findById(entryDetail.getId()) != null) {
            entryDetailRepository.save(entryDetail);
        }else {
            throw new ResourceNotFoundException(clase);
        }
    }

    @Override
    public void deleteEntryDetail(int id) {
        entryDetailRepository.deleteById(id);
    }

    @Override
    public void softDeleteEntryDetail(int id) throws ResourceNotFoundException {
        EntryDetail entryDetail = entryDetailRepository.findById(id);
        if (entryDetail != null) {
            entryDetail.setStatus(0);
            entryDetailRepository.save(entryDetail);
        }else{
            throw new ResourceNotFoundException(clase);
        }
    }
}
