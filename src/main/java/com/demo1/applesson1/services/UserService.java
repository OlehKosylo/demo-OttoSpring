package com.demo1.applesson1.services;

import com.demo1.applesson1.dto.UserResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    List<UserResponse> getAllUsers();

    UserResponse getUser(Integer id);

    void delete(Integer id);

    String uploadAvatar(MultipartFile file);
}
