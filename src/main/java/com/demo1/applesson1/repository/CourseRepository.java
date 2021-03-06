package com.demo1.applesson1.repository;

import com.demo1.applesson1.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findAllByGenre(String genre);

    Optional<Course> findByTitle(String title);

    @Query(value = "SELECT * FROM Course WHERE title like CONCAT('%',:titleValue,'%')", nativeQuery = true)
    List<Course> findAllByTitle(String titleValue);

    List<Course> findAllByGenreOrderByTitleAsc(String genreValue);

    List<Course> findAllByGenreOrderByTitleDesc(String genreValue);

    List<Course> findAllByGenreOrderByPriceAsc(String genreValue);

    List<Course> findAllByGenreOrderByPriceDesc(String genreValue);

    @Query(value = "SELECT * FROM Course WHERE genre LIKE :genreValue AND Price <= :priceValue", nativeQuery = true)
    List<Course> findAllByGenreWherePrice(String genreValue,int priceValue);

    boolean existsByTitle(String username);
}
