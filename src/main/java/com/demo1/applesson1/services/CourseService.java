package com.demo1.applesson1.services;


import com.demo1.applesson1.dto.Request.CommentRequest;
import com.demo1.applesson1.dto.Response.CommentResponse;
import com.demo1.applesson1.dto.Response.CourseResponse;
import java.util.List;

public interface CourseService {
    List<CourseResponse> getListCourses(String genre);

    List<CourseResponse> getMyListCourses(int userId);

    CourseResponse getMyCourse(long courseId, int userId);

    List<CourseResponse> getWantedCourses(String title);

    CourseResponse getCourse(String title, int userId);

    List<CourseResponse> getListCoursesByTitle(String genre, boolean statusForSort);

    List<CourseResponse> getListCoursesByPrice(String genre, boolean statusForSort);

    List<CourseResponse>  getListCoursesByLimitPrice(String genre, int price);

    CommentResponse setComment(CommentRequest CommentRequest);

    void deleteComment(int commentId);
}
