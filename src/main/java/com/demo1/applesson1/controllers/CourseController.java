package com.demo1.applesson1.controllers;

import com.demo1.applesson1.dto.Request.CommentRequest;
import com.demo1.applesson1.dto.Request.CourseRequest;
import com.demo1.applesson1.dto.Request.IdUserCourseRequest;
import com.demo1.applesson1.dto.Response.CommentResponse;
import com.demo1.applesson1.dto.Response.CourseResponse;
import com.demo1.applesson1.services.CourseService;
import com.demo1.applesson1.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseController {

    private final UserService userService;
    private final CourseService courseService;

    @PostMapping("/create")
    public HttpEntity<CourseResponse> create(@Valid @RequestBody CourseRequest courseRequest) {

        return new ResponseEntity<>(userService.uploadCourse(courseRequest), HttpStatus.OK);
    }

    @GetMapping("/list")
    public HttpEntity<List<CourseResponse>> getListCourses(@RequestParam String genre) {

        return new ResponseEntity<>(courseService.getListCourses(genre), HttpStatus.OK);
    }

    @GetMapping("/listByTitle")
    public HttpEntity<List<CourseResponse>> getListCoursesByTitle(@RequestParam String genre,
                                                                  @RequestParam boolean statusForSort) {

        return new ResponseEntity<>(courseService.getListCoursesByTitle(genre, statusForSort), HttpStatus.OK);
    }

    @GetMapping("/listByPrice")
    public HttpEntity<List<CourseResponse>> getListCoursesByPrice(@RequestParam String genre,
                                                                  @RequestParam boolean statusForSort) {

        return new ResponseEntity<>(courseService.getListCoursesByPrice(genre, statusForSort), HttpStatus.OK);
    }

    @GetMapping("/listByLimitPrice")
    public HttpEntity<List<CourseResponse>> getListCoursesByLimitPrice(@RequestParam String genre,
                                                                       @RequestParam int price) {

        return new ResponseEntity<>(courseService.getListCoursesByLimitPrice(genre, price), HttpStatus.OK);
    }

    @GetMapping("/getCourse")
    public HttpEntity<CourseResponse> getCourse(@RequestParam String title, @RequestParam int userId) {

        return new ResponseEntity<>(courseService.getCourse(title, userId), HttpStatus.OK);
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
    public HttpEntity<CourseResponse> getMyCourse(@RequestParam int courseId, @RequestParam int userId) {

        return new ResponseEntity<>(courseService.getMyCourse(courseId, userId), HttpStatus.OK);
    }

    @PostMapping("/setBoughtCourse")
    public HttpStatus setBoughtCourse(@Valid @RequestBody IdUserCourseRequest id) {

        userService.setBoughtCourse(id.getUserId(), id.getCourseId());
        return HttpStatus.OK;
    }

    @PostMapping("/setComment")
    public HttpEntity<CommentResponse> setComment(@Valid @RequestBody CommentRequest commentRequest) {

        return new ResponseEntity<>(courseService.setComment(commentRequest), HttpStatus.OK);
    }

    @GetMapping("/deleteComment")
    public HttpStatus deleteComment(@RequestParam int commentId) {

        courseService.deleteComment(commentId);
        return HttpStatus.OK;
    }

}
