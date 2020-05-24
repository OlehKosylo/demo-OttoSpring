package com.demo1.applesson1.repository;

import com.demo1.applesson1.models.Commentaries;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentariesRepository  extends JpaRepository<Commentaries, Integer> {

    List<Commentaries> findAllByCourseId(long courseId);

    @Override
    void deleteById(Integer integer);
}
