package com.demo1.applesson1.repository;

import com.demo1.applesson1.dto.CourseResponse;
import com.demo1.applesson1.models.Course;
import com.demo1.applesson1.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

   List<Course> findAllByGenre(String genre);

    boolean existsByTitle(String username);
}
