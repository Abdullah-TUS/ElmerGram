package com.elmergram.repositories;

import com.elmergram.models.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity,Integer> {

    @Query("SELECT COUNT(f) FROM FollowEntity f WHERE f.following.id = ?1")
    long countFollowers( int userId);

    @Query("SELECT COUNT(f) FROM FollowEntity f WHERE f.follower.id = ?1")
    long countFollowing( int userId);

    boolean existsByFollower_IdAndFollowing_Id(int follower, int following);
    void deleteByFollower_IdAndFollowing_Id(int followerId, int followingId);

}
