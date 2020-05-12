package com.demo1.applesson1.security;


import com.demo1.applesson1.models.User;
import com.demo1.applesson1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) {
            User user = userRepository.findByUsername(login)
                .orElseThrow(() ->
                        new RuntimeException("User not found [login: " + login + "]")
                );

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserByIdToUserDetails(Integer id) {
        return UserPrincipal.create(loadUserById(id));
    }


    @Transactional
    public User loadUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found [id: " + id + "]")
        );
    }
}
