package com.devnous.erp.ServiceImpl;

import com.devnous.erp.Entity.SalesDetail;
import com.devnous.erp.Exceptions.ResourceNotFoundException;
import com.devnous.erp.Repository.SalesDetailRepository;
import com.devnous.erp.Service.SalesDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("salesDetailService")
public class SalesDetailServiceImpl implements SalesDetailService {
    @Autowired
    @Qualifier("salesDetailRepository")
    SalesDetailRepository salesDetailRepository;
    private String clase = SalesDetail.class.getSimpleName();

    @Override
    public void createSalesDetail(SalesDetail salesDetail) {
        salesDetailRepository.save(salesDetail);
    }

    @Override
    public SalesDetail readSalesDetail(int id) {
        SalesDetail salesDetail = salesDetailRepository.findById(id);
        if(salesDetail == null){
            throw new ResourceNotFoundException(clase);
        }
        return salesDetail;
    }

    @Override
    public List<SalesDetail> readAllSalesDetail() {
        return salesDetailRepository.findAll();
    }

    @Override
    public void updateSalesDetail(SalesDetail salesDetail) {
        if (salesDetailRepository.findById(salesDetail.getId()) != null) {
            salesDetailRepository.save(salesDetail);
        }else{
            throw new ResourceNotFoundException(clase);
        }
    }

    @Override
    public void deleteSalesDetail(int id) {
        salesDetailRepository.deleteById(id);
    }

    @Override
    public void softDeleteSalesDetail(int id) {
        SalesDetail salesDetail = salesDetailRepository.findById(id);
        if (salesDetail != null) {
            salesDetail.setStatus(0);
            salesDetailRepository.save(salesDetail);
        }else {
            throw new ResourceNotFoundException(clase);
        }
    }
}
