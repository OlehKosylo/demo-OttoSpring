package com.demo1.applesson1.services.implentation;

import com.demo1.applesson1.dto.Request.CommentRequest;
import com.demo1.applesson1.dto.Response.CommentResponse;
import com.demo1.applesson1.dto.Response.CourseResponse;
import com.demo1.applesson1.models.Commentaries;
import com.demo1.applesson1.models.Course;
import com.demo1.applesson1.models.User;
import com.demo1.applesson1.repository.CommentariesRepository;
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
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CoursesServiceImp implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CommentariesRepository commentariesRepository;

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
    public CourseResponse getMyCourse(long courseId, int userId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException(String.format("Course with id: %s not found!", courseId)));

        List<Commentaries> commentariesOfDB = course.getCommentaries();

        List<Commentaries> commentaries = commentariesWithCheckedCommentStatus(commentariesOfDB, userId);

        course.setCommentaries(commentaries);

        return CourseResponse.builder()
                .id(course.getId())
                .downloadURL(course.getLinkOnVideo())
                .description(course.getDescription())
                .price(course.getPrice())
                .title(course.getTitle())
                .genre(course.getGenre())
                .commentaries(course.getCommentaries())
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
        Course course = courseRepository.findByTitle(title).orElseThrow(() -> new RuntimeException(String.format("Course with title: %s not found!", title)));

        int statusForView = statusForViewBuyOrOpen(course, userId);

        return CourseResponse.builder()
                .genre(course.getGenre())
                .title(course.getTitle())
                .price(course.getPrice())
                .description(course.getDescription())
                .id(course.getId())
                .statusForCheckIfUserHasThisCourse(statusForView)
                .commentaries(course.getCommentaries())
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

    @Override
    public CommentResponse setComment(CommentRequest commentRequest) {

        int userId = commentRequest.getUserId();
        long courseId = commentRequest.getCourseId();

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException(String.format("User with id: %s not found!", userId)));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException(String.format("Course with title: %s not found!", courseId)));

        Commentaries comment = Commentaries.builder()
                .userId(userId)
                .text(commentRequest.getText())
                .ownerName(user.getName_surname())
                .course(course)
                .build();

        Commentaries commentOfDB = commentariesRepository.save(comment);

        List<Commentaries> courseCommentaries = course.getCommentaries();
        courseCommentaries.add(commentOfDB);

        course.setCommentaries(courseCommentaries);

        courseRepository.save(course);

        Commentaries com = commentWithCheckedCommentStatus(comment, userId);

        return CommentResponse.builder()
                .id(com.getId())
                .userId(com.getUserId())
                .ownerName(com.getOwnerName())
                .text(com.getText())
                .statusForDeleteView(com.getStatusForDeleteView())
                .build();
    }

    @Override
    public void deleteComment(int commentId) {
        commentariesRepository.deleteById(commentId);
    }


    private Commentaries commentWithCheckedCommentStatus(Commentaries comment, int userId) {
        Commentaries newComment = comment;

        if (comment.getUserId() == userId) {
            newComment.setStatusForDeleteView(1);
        } else {
            newComment.setStatusForDeleteView(0);
        }

        return newComment;
    }

    private List<Commentaries> commentariesWithCheckedCommentStatus(List<Commentaries> commentariesOfDB, int userId) {
        List<Commentaries> newList = new ArrayList<>();

        for (Commentaries com : commentariesOfDB) {
            Commentaries comment = commentWithCheckedCommentStatus(com, userId);
            newList.add(comment);
        }

        return newList;
    }

    private int statusForViewBuyOrOpen(Course originalCourse, int userId) {
        List<User> users = originalCourse.getUsers();

        for (int j = 0; j < users.size(); j++) {
            User user = users.get(j);

            if (userId == user.getId()) {
                return 1;
            }
        }
        return 0;
    }

}
