package com.demo1.applesson1.services.implentation;

import com.demo1.applesson1.dto.UserResponse;
import com.demo1.applesson1.models.User;
import com.demo1.applesson1.repository.UserRepository;
import com.demo1.applesson1.security.JwtTokenProvider;
import com.demo1.applesson1.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(user -> UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build()).collect(Collectors.toList());
    }

    @Override
    public UserResponse getUser(Integer id) {

        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("User with id: %s not found!", id)));

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();

    }



    @Override
    public void delete(Integer id) {

        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("User with id: %s not found!", id)));

        userRepository.delete(user);
    }

    @Override
    public String uploadAvatar(MultipartFile file) {
        return null;
    }
}
