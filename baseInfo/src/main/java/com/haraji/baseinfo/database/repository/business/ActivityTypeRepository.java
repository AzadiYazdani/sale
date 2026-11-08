package com.haraji.baseinfo.database.repository.business;

import com.haraji.baseinfo.database.entity.business.ActivityTypeEntity;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityTypeRepository extends JpaRepository<ActivityTypeEntity, Long> {

    Optional<ActivityTypeEntity> findById(@Nonnull Long id);

    List<ActivityTypeEntity> findAllByTitleContains(String titleValue);
}
