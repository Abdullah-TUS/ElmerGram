package com.elmergram.repositories;

import com.elmergram.dto.PostDto;
import com.elmergram.models.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {

    Page<PostEntity> findByUser_Id(Integer userId, Pageable pageable);

    @Query("""
        SELECT new com.elmergram.dto.PostDto$Summary(p.id, p.media, u.username)
        FROM PostEntity p
        JOIN p.user u
        """)
    Page<PostDto.Summary> findAllPostSummaries(Pageable pageable);

}
