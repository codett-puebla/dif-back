package com.devnous.erp.Service;

import com.devnous.erp.Entity.DepartureDetail;
import com.devnous.erp.Exceptions.ResourceNotFoundException;

import java.util.List;

public interface DepartureDetailService {
    void createDepartureDetail(DepartureDetail departureDetail);

    DepartureDetail readDepartureDetail(int id) throws ResourceNotFoundException;

    List<DepartureDetail> readAllDepartureDetail();

    void updateDepartureDetail(DepartureDetail departureDetail) throws ResourceNotFoundException;

    void deleteDepartureDetail(int id);

    void softDeleteDepartureDetail(int id) throws ResourceNotFoundException;
}
