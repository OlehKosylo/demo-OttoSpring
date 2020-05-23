package com.demo1.applesson1.repository;

import com.demo1.applesson1.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

   List<Course> findAllByGenre(String genre);

   @Query(value = "SELECT * FROM Course WHERE title like CONCAT('%',:titleValue,'%')", nativeQuery = true)
   List<Course> findAllByTitle(String titleValue);

    boolean existsByTitle(String username);
}