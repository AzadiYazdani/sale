package com.haraji.security.database.repository;

import com.haraji.security.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    Optional<List<UserEntity>> findAllByUsernameContains(String titlePart);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
