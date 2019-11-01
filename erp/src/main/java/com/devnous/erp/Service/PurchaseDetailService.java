package com.devnous.erp.Service;

import com.devnous.erp.Entity.PurchaseDetail;
import com.devnous.erp.Exceptions.ResourceNotFoundException;

import java.util.List;

public interface PurchaseDetailService {
    void createPurchaseDetail(PurchaseDetail purchaseDetail);

    PurchaseDetail readPurchaseDetail(int id) throws ResourceNotFoundException;

    List<PurchaseDetail> readAllPurchaseDetail();

    void updatePurchaseDetail(PurchaseDetail purchaseDetail) throws ResourceNotFoundException;

    void deletePurchaseDetail(int id);

    void softDeletePurchaseDetail(int id) throws ResourceNotFoundException;
}
