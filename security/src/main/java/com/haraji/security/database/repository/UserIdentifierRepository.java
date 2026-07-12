package com.haraji.security.database.repository;

import com.haraji.security.constant.IdentifierType;
import com.haraji.security.database.entity.UserIdentifierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserIdentifierRepository  extends JpaRepository<UserIdentifierEntity, Long> {

    Optional<UserIdentifierEntity> findByTypeAndValue(
            IdentifierType type,
            String value);

    Optional<UserIdentifierEntity> findByTypeAndValueAndEnabledTrue(
            IdentifierType type,
            String value);

    boolean existsByTypeAndValue(
            IdentifierType type,
            String value);

    List<UserIdentifierEntity> findAllByUserId(Long userId);

    Optional<UserIdentifierEntity> findByUserIdAndPrimaryIdentifierTrue(Long userId);

}
