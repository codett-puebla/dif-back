package com.devnous.erp.Service;

import com.devnous.erp.Entity.PurchaseHeader;
import com.devnous.erp.Entity.Quotation;
import com.devnous.erp.Entity.SalesHeader;
import com.devnous.erp.Exceptions.ResourceNotFoundException;

import java.util.List;

public interface QuotationService {
    void createQuotation(Quotation quotation);

    Quotation readQuotation(int id) throws ResourceNotFoundException;

    List<Quotation> readAllActiveQuotations();

    List<Quotation> readAllRemovedQuotations();

    void updateQuotation(Quotation quotation) throws ResourceNotFoundException;

    void deleteQuotation(int id);

    void softDeleteQuotation(int id) throws ResourceNotFoundException;

    List<PurchaseHeader> makePurchaseFromOrder(int idOrder);
}
