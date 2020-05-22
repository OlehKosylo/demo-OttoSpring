package com.demo1.applesson1.controllers;

import com.demo1.applesson1.dto.CourseRequest;
import com.demo1.applesson1.dto.CourseResponse;
import com.demo1.applesson1.dto.IdUserCourseRequest;
import com.demo1.applesson1.services.CourseService;
import com.demo1.applesson1.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseController {

    private final UserService userService;
    private final CourseService courseService;

    @PostMapping("/create")
    public HttpEntity<CourseResponse> create(@RequestBody CourseRequest courseRequest) {

        return new ResponseEntity<>(userService.uploadCourse(courseRequest), HttpStatus.OK);
    }

    @GetMapping("/list")
    public HttpEntity<List<CourseResponse>> getListCourses(@RequestParam String genre, @RequestParam int userId) {

        return new ResponseEntity<>(courseService.getListCourses(genre, userId), HttpStatus.OK);
    }

    @GetMapping("/searchCourse")
    public HttpEntity<List<CourseResponse>> getWantedCourses(@RequestParam String title) {

        return new ResponseEntity<>(courseService.getWantedCourses(title), HttpStatus.OK);
    }

    @GetMapping("/myList")
    public HttpEntity<List<CourseResponse>> getListCourses(@RequestParam int userId) {

        return new ResponseEntity<>(courseService.getMyListCourses(userId), HttpStatus.OK);
    }

    @GetMapping("/myCourse")
    public HttpEntity<CourseResponse> getCourse(@RequestParam int courseId) {

        return new ResponseEntity<>(courseService.getCourse(courseId), HttpStatus.OK);
    }

    @PostMapping("/setBoughtCourse")
    public HttpStatus setBoughtCourse(@RequestBody IdUserCourseRequest id) {

        userService.setBoughtCourse(id.getUserId(), id.getCourseId());
        return HttpStatus.OK;
    }


}
