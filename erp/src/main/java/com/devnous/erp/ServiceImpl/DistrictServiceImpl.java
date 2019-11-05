package com.devnous.erp.ServiceImpl;

import com.devnous.erp.Entity.District;
import com.devnous.erp.Exceptions.ResourceNotFoundException;
import com.devnous.erp.Repository.DistrictRepository;
import com.devnous.erp.Service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("districtService")
public class DistrictServiceImpl implements DistrictService {

    @Autowired
    @Qualifier("districtRepository")
    private DistrictRepository districtRepository;

    private String clase = District.class.getSimpleName();


    @Override
    public District readDistrict(int id) throws ResourceNotFoundException{
        District district = districtRepository.findById(id);
        if(district == null){
            throw new ResourceNotFoundException(clase);
        }

        return district;
    }
}
