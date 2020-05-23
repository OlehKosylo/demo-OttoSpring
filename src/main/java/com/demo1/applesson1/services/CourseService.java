package com.demo1.applesson1.services;


import com.demo1.applesson1.dto.CourseResponse;
import java.util.List;

public interface CourseService {
    List<CourseResponse> getListCourses(String genre, int userId);

    List<CourseResponse> getMyListCourses(int userId);

    CourseResponse getCourse(long courseId);

    List<CourseResponse> getWantedCourses(String title);

}