package com.devnous.erp.Repository;

import com.devnous.erp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("userRepository")
public interface UserRepository  extends JpaRepository<User, Serializable> {

    User findById(int id);

    List<User> findByStatus(int status);
}
