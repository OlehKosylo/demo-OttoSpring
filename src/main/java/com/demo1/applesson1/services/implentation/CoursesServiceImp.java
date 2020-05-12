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
        return courseRepository.findAllByGenre(genre).stream().map(course -> CourseResponse.builder()
                .id(course.getId())
                .genre(course.getGenre())
                .title(course.getTitle())
                .price(course.getPrice())
                .downloadURL(course.getLinkOnVideo())
                .description(course.getDescription())
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


    @Override
    public boolean existsId(long id) {
        return courseRepository.existsById(id);
    }


//    @Override
//    public List<CourseResponse> getListCourses(String genre) {
//
//        List<Course> coursesList = courseRepository.findAllByGenre(genre);
//        Iterator<Course> iter = coursesList.iterator();
//        List<CourseResponse> courseResponseList = null;
//
//        while (iter.hasNext()) {
//            Course course = iter.next();
//
//            CourseResponse courseIter = CourseResponse.builder()
//                    .title(course.getTitle())
//                    .description(course.getDescription())
//                    .downloadURL(course.getLinkOnVideo())
//                    .price(course.getPrice())
//                    .genre(course.getGenre())
//                    .build();
//            courseResponseList.add(courseIter);
//        }
//
//        return courseResponseList;
//    }


}
