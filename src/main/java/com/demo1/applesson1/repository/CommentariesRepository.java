package com.demo1.applesson1.repository;

import com.demo1.applesson1.models.Commentaries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentariesRepository  extends JpaRepository<Commentaries, Integer> {

    void deleteById(Integer integer);
}
