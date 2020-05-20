package com.demo1.applesson1.services;

import com.demo1.applesson1.dto.CourseRequest;
import com.demo1.applesson1.dto.CourseResponse;
import com.demo1.applesson1.dto.UserRequest;
import com.demo1.applesson1.dto.UserResponse;
import com.stripe.exception.StripeException;

public interface UserService {
    UserResponse getUserInfoForMainPage(int id);

    UserResponse getUserInfoForProfilePage(int id) throws StripeException;

    UserResponse editInfoUser(UserRequest user);

    UserResponse editUserPhoto(UserRequest editUserInfoResponse);

    CourseResponse uploadCourse(CourseRequest courseRequest);

    void setBoughtCourse(int userId, long courseId);



//    List<UserResponse> getAllUsers();

//    UserResponse getUser(Integer id);
//
//    void delete(Integer id);
//
//    void updateUserName(Integer id, String newName);

//    String uploadAvatar(MultipartFile file, Integer userId);
//
//    String uploadVideo (CourseRequest courseRequest, Integer userId);
}
