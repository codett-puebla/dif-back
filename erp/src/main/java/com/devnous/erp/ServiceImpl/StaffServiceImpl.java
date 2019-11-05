package com.devnous.erp.ServiceImpl;

import com.devnous.erp.Entity.Staff;
import com.devnous.erp.Exceptions.ResourceNotFoundException;
import com.devnous.erp.Repository.StaffRepository;
import com.devnous.erp.Service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("staffService")
public class StaffServiceImpl implements StaffService {
    @Autowired
    @Qualifier("staffRepository")
    StaffRepository staffRepository;
    private String clase = Staff.class.getSimpleName();

    @Override
    public void createStaff(Staff staff) {
        staffRepository.save(staff);
    }

    @Override
    public Staff readStaff(int id) {
        Staff staff = staffRepository.findById(id);
        if(staff == null){
            throw new ResourceNotFoundException(clase);
        }
        return staff;
    }

    @Override
    public List<Staff> readAllActiveStaff() {
        return staffRepository.findByStatus(1);
    }

    @Override
    public List<Staff> readAllRemovedStaff() {
        return staffRepository.findByStatus(0);
    }

    @Override
    public void updateStaff(Staff staff) {
        if (staffRepository.findById(staff.getId()) != null) {
            staffRepository.save(staff);
        }else{
            throw new ResourceNotFoundException(clase);
        }
    }

    @Override
    public void deleteStaff(int id) {
        staffRepository.deleteById(id);
    }

    @Override
    public void softDeleteStaff(int id) {
        Staff staff = staffRepository.findById(id);
        if (staff != null) {
            staff.setStatus(0);
            staffRepository.save(staff);
        }else{
            throw new ResourceNotFoundException(clase);
        }
    }
}
