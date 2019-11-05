package com.devnous.erp.Service;

import com.devnous.erp.Entity.Staff;
import com.devnous.erp.Exceptions.ResourceNotFoundException;

import java.util.List;

public interface StaffService {
    void createStaff(Staff staff);

    Staff readStaff(int id) throws ResourceNotFoundException;

    List<Staff> readAllActiveStaff();

    List<Staff> readAllRemovedStaff();

    void updateStaff(Staff staff) throws ResourceNotFoundException;

    void deleteStaff(int id);

    void softDeleteStaff(int id) throws ResourceNotFoundException;
}
