package com.haraji.security.database.repository;

import com.haraji.security.database.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
    Optional<PersonEntity> findById(Long id);
    Optional<PersonEntity> findByNationalCode(String nationalCode);
}
