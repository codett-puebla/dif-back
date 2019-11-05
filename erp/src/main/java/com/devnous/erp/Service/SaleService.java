package com.devnous.erp.Service;

import com.devnous.erp.Entity.Item;
import com.devnous.erp.Entity.SalesHeader;
import com.devnous.erp.Exceptions.ResourceNotFoundException;
import com.devnous.erp.Exceptions.TransactionInterruptedException;
import com.devnous.erp.Exceptions.UniqueAttributeException;

import java.util.List;

public interface SaleService {
    void createSalesHeader(SalesHeader salesHeader) throws UniqueAttributeException, TransactionInterruptedException;

    SalesHeader readSalesHeader(int id) throws ResourceNotFoundException;

    List<SalesHeader> readAllActiveSalesHeader();

    List<SalesHeader> readAllRemovedSalesHeader();

    void updateSalesHeader(SalesHeader salesHeader) throws ResourceNotFoundException;

    void deleteSalesHeader(int id);

    void softDeleteSales(int id) throws ResourceNotFoundException;

    boolean isAValidFolioAndSeries(String folio, String series);
}
