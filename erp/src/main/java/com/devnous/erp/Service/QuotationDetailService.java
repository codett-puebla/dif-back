package com.devnous.erp.Service;

import com.devnous.erp.Entity.QuotationDetail;
import com.devnous.erp.Exceptions.ResourceNotFoundException;

import java.util.List;

public interface QuotationDetailService {
    void createQuotationDetail(QuotationDetail quotationDetail);

    QuotationDetail readQuotationDetail(int id) throws ResourceNotFoundException;

    List<QuotationDetail> readAllQuotationDetail();

    void updateQuotationDetail(QuotationDetail quotationDetail) throws ResourceNotFoundException;

    void deleteQuotationDetail(int id);

    void softDeleteQuotationDetail(int id) throws ResourceNotFoundException;
}
