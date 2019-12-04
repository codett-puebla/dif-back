package com.devnous.erp.ServiceImpl;

import com.devnous.erp.Entity.User;
import com.devnous.erp.Exceptions.ResourceNotFoundException;
import com.devnous.erp.Repository.UserRepository;
import com.devnous.erp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    @Qualifier("userRepository")
    UserRepository userRepository;
    private String clase  = User.class.getSimpleName();

    @Override
    public void createUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setCreate_at(new Date());
        userRepository.save(user);
    }

    @Override
    public User readUser(int id) throws ResourceNotFoundException {
        User user = userRepository.findById(id);
        if(user == null){
            throw new ResourceNotFoundException(clase);
        }
        return user;
    }

    @Override
    public List<User> readAllActiveUser() {
        return userRepository.findByStatus(User.ACTIVO);
    }

    @Override
    public List<User> readAllRemovedUser() {
        return userRepository.findByStatus(User.ELIMINADO);
    }

    @Override
    public void updateUser(User user) throws ResourceNotFoundException {
        if(userRepository.findById(user.getId()) != null){
            userRepository.save(user);
        }else{
            throw new ResourceNotFoundException(clase);
        }
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User verifyEmail(String email) {
        User user = userRepository.findByUsername(email);
        return user;
    }

    @Override
    public User getUserForEmail(String email) {
        return userRepository.findByUsername(email);
    }

    @Override
    public void softDeleteUser(int id) throws ResourceNotFoundException {
        User user = userRepository.findById(id);
        if(user != null){
            user.setStatus(User.ELIMINADO);
            userRepository.save(user);
        }else{
            throw new ResourceNotFoundException(clase);
        }
    }
}
