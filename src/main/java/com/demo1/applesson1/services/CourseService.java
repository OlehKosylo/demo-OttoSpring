package com.demo1.applesson1.services;


import com.demo1.applesson1.dto.CourseResponse;
import java.util.List;

public interface CourseService {
    List<CourseResponse> getListCourses(String genre);

    List<CourseResponse> getMyListCourses(int userId);

    CourseResponse getMyCourse(long courseId);

    List<CourseResponse> getWantedCourses(String title);

    CourseResponse getCourse(String title, int userId);

    List<CourseResponse> getListCoursesByTitle(String genre, boolean statusForSort);

    List<CourseResponse> getListCoursesByPrice(String genre, boolean statusForSort);

    List<CourseResponse>  getListCoursesByLimitPrice(String genre, int price);
}
