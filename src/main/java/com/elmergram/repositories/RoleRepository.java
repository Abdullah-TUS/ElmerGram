package com.elmergram.repositories;

import com.elmergram.enums.RoleName;
import com.elmergram.models.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByName(RoleName name);
}

