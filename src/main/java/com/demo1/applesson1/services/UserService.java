package com.demo1.applesson1.services;

import com.demo1.applesson1.dto.Request.CourseRequest;
import com.demo1.applesson1.dto.Response.CourseResponse;
import com.demo1.applesson1.dto.Request.UserRequest;
import com.demo1.applesson1.dto.Response.UserResponse;
import com.stripe.exception.StripeException;

public interface UserService {
    UserResponse getUserInfoForMainPage(int id);

    UserResponse getUserInfoForProfilePage(int id) throws StripeException;

    UserResponse editInfoUser(UserRequest user);

    UserResponse editUserPhoto(UserRequest editUserInfoResponse);

    CourseResponse uploadCourse(CourseRequest courseRequest);

    void setBoughtCourse(int userId, long courseId);

}
