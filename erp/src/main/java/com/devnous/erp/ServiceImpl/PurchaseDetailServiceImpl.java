package com.devnous.erp.ServiceImpl;

import com.devnous.erp.Entity.PurchaseDetail;
import com.devnous.erp.Exceptions.ResourceNotFoundException;
import com.devnous.erp.Repository.PurchaseDetailRepository;
import com.devnous.erp.Service.PurchaseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl implements PurchaseDetailService{
    @Autowired
    @Qualifier("purchaseDetailRepository")
    PurchaseDetailRepository purchaseDetailRepository;
    private String clase = PurchaseDetail.class.getSimpleName();

    @Override
    public void createPurchaseDetail(PurchaseDetail purchaseDetail) {
        purchaseDetailRepository.save(purchaseDetail);
    }

    @Override
    public PurchaseDetail readPurchaseDetail(int id) {
        PurchaseDetail purchaseDetail = purchaseDetailRepository.findById(id);
        if(purchaseDetail == null){
            throw new ResourceNotFoundException(clase);
        }
        return purchaseDetail;
    }

    @Override
    public List<PurchaseDetail> readAllPurchaseDetail() {
        return purchaseDetailRepository.findAll();
    }

    @Override
    public void updatePurchaseDetail(PurchaseDetail purchaseDetail) {
        if (purchaseDetailRepository.findById(purchaseDetail.getId()) != null) {
            purchaseDetailRepository.save(purchaseDetail);
        }else{
            throw new ResourceNotFoundException(clase);
        }
    }

    @Override
    public void deletePurchaseDetail(int id) {
        purchaseDetailRepository.deleteById(id);
    }

    @Override
    public void softDeletePurchaseDetail(int id) {
        PurchaseDetail purchaseDetail  =purchaseDetailRepository.findById(id);
        if (purchaseDetail != null) {
            purchaseDetail.setStatus(0);
            purchaseDetailRepository.save(purchaseDetail);
        }else{
            throw new ResourceNotFoundException(clase);
        }
    }
}
