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

import java.util.ArrayList;
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
    public List<CourseResponse> getListCourses(String genre, int userId) {

        List<Course> originalCourse = courseRepository.findAllByGenre(genre);
        List<Course> newListCourse = new ArrayList<Course>();

        //Checking the status of the course for UI view
        for (int i = 0; i < originalCourse.size(); i++) {

            Course course = originalCourse.get(i);
            List<User> users = course.getUsers();

            for (int j = 0; j < users.size(); j++) {
                User user = users.get(j);

                if (userId == user.getId()) {
                    course.setStatusForCheckIfUserHasThisCourse(1);
                    break;
                } else {
                    course.setStatusForCheckIfUserHasThisCourse(0);
                }
            }
            newListCourse.add(course);
        }

        return newListCourse.stream().map(course ->
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
    public CourseResponse getCourse(long courseId) {
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

}
