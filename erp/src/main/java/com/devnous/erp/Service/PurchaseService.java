package com.devnous.erp.Service;

import com.devnous.erp.Entity.PurchaseHeader;
import com.devnous.erp.Exceptions.ResourceNotFoundException;

import java.util.List;

public interface PurchaseService {
    void createPurchaseHeader(PurchaseHeader purchaseHeader);

    PurchaseHeader readPurchaseHeader(int id) throws ResourceNotFoundException;

    List<PurchaseHeader> readAllActivePurchaseHeader(int idCompany);

    List<PurchaseHeader> readAllRemovedPurchaseHeader(int idCompany);

    void updatePurchaseHeader(PurchaseHeader purchaseHeader) throws ResourceNotFoundException;

    void deletePurchaseHeader(int id);

    void softDeletePurchaseHeader(int id) throws ResourceNotFoundException;
}
