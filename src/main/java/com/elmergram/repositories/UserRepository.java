package com.elmergram.repositories;

import com.elmergram.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    UserEntity findByUsernameIgnoreCase(String username);

    boolean existsByUsername(String username);
}
