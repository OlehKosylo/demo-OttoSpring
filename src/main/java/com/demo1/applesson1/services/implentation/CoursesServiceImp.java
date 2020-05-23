package com.demo1.applesson1.services.implentation;

import com.demo1.applesson1.dto.CourseResponse;
import com.demo1.applesson1.models.Course;
import com.demo1.applesson1.models.User;
import com.demo1.applesson1.repository.CourseRepository;
import com.demo1.applesson1.repository.UserRepository;
import com.demo1.applesson1.services.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CoursesServiceImp implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public List<CourseResponse> getListCourses(String genre) {

        List<Course> originalCourse = courseRepository.findAllByGenre(genre);

        return originalCourse.stream().map(course ->
                CourseResponse.builder()
                        .id(course.getId())
                        .title(course.getTitle())
                        .price(course.getPrice())
                        .genre(course.getGenre())
                        .build()).collect(Collectors.toList());
    }

    @Override
    public List<CourseResponse> getMyListCourses(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException(String.format("User with id: %s not found!", userId)));

        List<CourseResponse> collect = user.getCourses().stream().map(course -> CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .build()).collect(Collectors.toList());

        return collect;
    }

    @Override
    public CourseResponse getMyCourse(long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException(String.format("Course with id: %s not found!", courseId)));

        return CourseResponse.builder()
                .id(course.getId())
                .downloadURL(course.getLinkOnVideo())
                .description(course.getDescription())
                .price(course.getPrice())
                .title(course.getTitle())
                .genre(course.getGenre())
                .build();
    }

    @Override
    public List<CourseResponse> getWantedCourses(String title) {
        List<Course> allByTitleCustom = courseRepository.findAllByTitle(title);

        return allByTitleCustom.stream().map(course ->
                CourseResponse.builder()
                        .id(course.getId())
                        .genre(course.getGenre())
                        .title(course.getTitle())
                        .price(course.getPrice())
                        .downloadURL(course.getLinkOnVideo())
                        .description(course.getDescription())
                        .statusForCheckIfUserHasThisCourse(course.getStatusForCheckIfUserHasThisCourse())
                        .build()).collect(Collectors.toList());
    }

    @Override
    public CourseResponse getCourse(String title, int userId) {

        Course courseOfDB = courseRepository.findByTitle(title).orElseThrow(() -> new RuntimeException(String.format("Course with title: %s not found!", title)));

        Course course = courseWithCheckedCourseStatus(courseOfDB, userId);

        return CourseResponse.builder()
                .genre(course.getGenre())
                .title(course.getTitle())
                .price(course.getPrice())
                .description(course.getDescription())
                .id(course.getId())
                .statusForCheckIfUserHasThisCourse(course.getStatusForCheckIfUserHasThisCourse())
                .build();

    }

    @Override
    public List<CourseResponse> getListCoursesByTitle(String genre, boolean statusForSort) {

        List<Course> list;

        if (statusForSort) {
            list = courseRepository.findAllByGenreOrderByTitleDesc(genre);
        } else {
            list = courseRepository.findAllByGenreOrderByTitleAsc(genre);
        }

        return list.stream().map(course ->
                CourseResponse.builder()
                        .id(course.getId())
                        .title(course.getTitle())
                        .price(course.getPrice())
                        .genre(course.getGenre())
                        .build()).collect(Collectors.toList());
    }

    @Override
    public List<CourseResponse> getListCoursesByPrice(String genre, boolean statusForSort) {

        List<Course> list;

        if (statusForSort) {
            list = courseRepository.findAllByGenreOrderByPriceDesc(genre);
        } else {
            list = courseRepository.findAllByGenreOrderByPriceAsc(genre);
        }

        return list.stream().map(course ->
                CourseResponse.builder()
                        .id(course.getId())
                        .title(course.getTitle())
                        .price(course.getPrice())
                        .genre(course.getGenre())
                        .build()).collect(Collectors.toList());
    }

    @Override
    public List<CourseResponse> getListCoursesByLimitPrice(String genre, int price) {
        List<Course> allByGenreWherePrice = courseRepository.findAllByGenreWherePrice(genre, price);

        return allByGenreWherePrice.stream().map(course ->
                CourseResponse.builder()
                        .id(course.getId())
                        .title(course.getTitle())
                        .price(course.getPrice())
                        .genre(course.getGenre())
                        .build()).collect(Collectors.toList());
    }

    private Course courseWithCheckedCourseStatus(Course originalCourse, int userId) {
        Course course = originalCourse;

        List<User> users = originalCourse.getUsers();

        for (int j = 0; j < users.size(); j++) {
            User user = users.get(j);

            if (userId == user.getId()) {
                course.setStatusForCheckIfUserHasThisCourse(1);
                break;
            } else {
                course.setStatusForCheckIfUserHasThisCourse(0);
            }
        }

        return course;
    }

}
