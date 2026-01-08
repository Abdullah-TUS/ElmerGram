package com.elmergram.repositories;

import com.elmergram.models.PostEntity;
import com.elmergram.models.ReactionEntity;
import com.elmergram.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReactionRepository extends JpaRepository<ReactionEntity,Integer> {
    List<ReactionEntity> findByPost_Id(int post);
    Optional<ReactionEntity> findByUserAndPost(UserEntity user,PostEntity post);
    Optional<ReactionEntity> findByUserAndPost(int userId,int postId);
}
