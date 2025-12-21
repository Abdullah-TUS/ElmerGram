package com.elmergram.repositories;

import com.elmergram.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {
    Page<Post> findByUserId(Integer userId, Pageable pageable);
}
