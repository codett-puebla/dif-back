package com.devnous.erp.Service;

import com.devnous.erp.Entity.User;
import com.devnous.erp.Exceptions.ResourceNotFoundException;

import java.util.List;

public interface UserService {
    void createUser(User user);

    User readUser(int id) throws ResourceNotFoundException;

    List<User> readAllActiveUser();

    List<User> readAllRemovedUser();

    void updateUser(User user) throws ResourceNotFoundException;

    void deleteUser(int id);

    void softDeleteUser(int id) throws ResourceNotFoundException;
}
