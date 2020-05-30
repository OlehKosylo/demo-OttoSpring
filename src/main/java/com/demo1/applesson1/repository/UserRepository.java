package com.demo1.applesson1.repository;

import com.demo1.applesson1.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findByStatusMailActivate(String token);

    Optional<User> findByMail(String email);

    boolean existsByUsername(String username);

    boolean existsByMail(String mail);
}
