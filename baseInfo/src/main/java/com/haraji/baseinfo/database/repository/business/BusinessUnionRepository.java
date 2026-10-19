package com.haraji.baseinfo.database.repository.business;

import com.haraji.baseinfo.database.entity.business.BusinessUnionEntity;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessUnionRepository extends JpaRepository<BusinessUnionEntity, Long> {

    Optional<BusinessUnionEntity> findById(Long id);

    List<BusinessUnionEntity> findAllByTitleContains(String titleValue);

    List<BusinessUnionEntity> findAllByCityId(@Min(1) Long cityId);

    Page<BusinessUnionEntity> findAllByCityId(@Min(1) Long cityId, Pageable pageable);
}
