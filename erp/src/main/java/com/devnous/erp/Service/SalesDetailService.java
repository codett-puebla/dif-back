package com.devnous.erp.Service;

import com.devnous.erp.Entity.SalesDetail;
import com.devnous.erp.Exceptions.ResourceNotFoundException;

import java.util.List;

public interface SalesDetailService {
    void createSalesDetail(SalesDetail salesDetail);

    SalesDetail readSalesDetail(int id) throws ResourceNotFoundException;

    List<SalesDetail> readAllSalesDetail();

    void updateSalesDetail(SalesDetail salesDetail) throws ResourceNotFoundException;

    void deleteSalesDetail(int id);

    void softDeleteSalesDetail(int id) throws ResourceNotFoundException;
}
