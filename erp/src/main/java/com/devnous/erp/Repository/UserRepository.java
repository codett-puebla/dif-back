package com.devnous.erp.Repository;

import com.devnous.erp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("userRepository")
public interface UserRepository  extends JpaRepository<User, Serializable> {

    User findById(int id);
    User findByUsername(String username);

    @Query(value = "Select * from user as u where u.status >= ?1 ORDER BY u.id", nativeQuery = true)
    List<User> findByStatus(int status);
}
