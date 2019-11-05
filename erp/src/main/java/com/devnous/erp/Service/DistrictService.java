package com.devnous.erp.Service;

import com.devnous.erp.Entity.District;
import com.devnous.erp.Exceptions.ResourceNotFoundException;

public interface DistrictService {

    District readDistrict(int id) throws ResourceNotFoundException;

}
