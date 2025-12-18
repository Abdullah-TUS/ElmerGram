package com.elmergram.repositories;

import com.elmergram.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsernameIgnoreCase(String username);


    boolean existsByUsername(String username);
}
