package com.devnous.erp.ServiceImpl;

import com.devnous.erp.Exceptions.ResourceNotFoundException;
import com.devnous.erp.Repository.DepartureDetailRepository;
import com.devnous.erp.Service.DepartureDetailService;
import com.devnous.erp.Entity.DepartureDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("departureDetailService")
public class DepartureDetailServiceImpl implements DepartureDetailService{
    @Autowired
    @Qualifier("departureDetailRepository")
    DepartureDetailRepository departureDetailRepository;
    private String clase = DepartureDetail.class.getSimpleName();

    @Override
    public void createDepartureDetail(DepartureDetail departureDetail) {
        departureDetailRepository.save(departureDetail);
    }

    @Override
    public DepartureDetail readDepartureDetail(int id) {
        DepartureDetail departureDetail = departureDetailRepository.findById(id);
        if(departureDetail == null){
            throw new ResourceNotFoundException(clase);
        }
        return departureDetail;
    }

    @Override
    public List<DepartureDetail> readAllDepartureDetail() {
        return departureDetailRepository.findAll();
    }

    @Override
    public void updateDepartureDetail(DepartureDetail departureDetail) {
        if (departureDetailRepository.findById(departureDetail.getId())!= null){
            departureDetailRepository.save(departureDetail);
        }else {
            throw new ResourceNotFoundException(clase);
        }
    }

    @Override
    public void deleteDepartureDetail(int id) {
        departureDetailRepository.deleteById(id);
    }

    @Override
    public void softDeleteDepartureDetail(int id) {
        DepartureDetail departureDetail = departureDetailRepository.findById(id);
        if (departureDetail!= null){
            departureDetail.setStatus(0);
            departureDetailRepository.save(departureDetail);
        }else{
            throw new ResourceNotFoundException(clase);
        }
    }
}
