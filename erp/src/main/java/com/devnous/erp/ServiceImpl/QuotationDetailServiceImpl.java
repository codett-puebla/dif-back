package com.devnous.erp.ServiceImpl;

import com.devnous.erp.Entity.QuotationDetail;
import com.devnous.erp.Exceptions.ResourceNotFoundException;
import com.devnous.erp.Repository.QuotationDetailRepository;
import com.devnous.erp.Service.QuotationDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("quotationDetailService")
public class QuotationDetailServiceImpl implements QuotationDetailService {
    @Autowired
    @Qualifier("quotationDetailRepository")
    QuotationDetailRepository quotationDetailRepository;
    private String clase = QuotationDetail.class.getSimpleName();

    @Override
    public void createQuotationDetail(QuotationDetail quotationDetail) {
        quotationDetailRepository.save(quotationDetail);
    }

    @Override
    public QuotationDetail readQuotationDetail(int id) {
        QuotationDetail quotationDetail = quotationDetailRepository.findById(id);
        if(quotationDetail == null){
            throw new ResourceNotFoundException(clase);
        }
        return quotationDetail;
    }

    @Override
    public List<QuotationDetail> readAllQuotationDetail() {
        return quotationDetailRepository.findAll();
    }

    @Override
    public void updateQuotationDetail(QuotationDetail quotationDetail) {
        if (quotationDetailRepository.findById(quotationDetail.getId()) != null) {
            quotationDetailRepository.save(quotationDetail);
        }else{
            throw new ResourceNotFoundException(clase);
        }
    }

    @Override
    public void deleteQuotationDetail(int id) {
        quotationDetailRepository.deleteById(id);
    }

    @Override
    public void softDeleteQuotationDetail(int id) {
        QuotationDetail quotationDetail = quotationDetailRepository.findById(id);
        if (quotationDetail != null) {
            quotationDetail.setStatus(0);
            quotationDetailRepository.save(quotationDetail);
        }else{
            throw new ResourceNotFoundException(clase);
        }
    }
}
