package com.demo1.applesson1.dbServices;

import com.demo1.applesson1.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDAO extends JpaRepository<User, Integer> {
    UserDetails findUserByUsername(String username);
}
