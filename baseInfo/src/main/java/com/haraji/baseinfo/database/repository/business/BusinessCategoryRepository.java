package com.haraji.baseinfo.database.repository.business;

import com.haraji.baseinfo.database.entity.business.BusinessCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessCategoryRepository extends JpaRepository<BusinessCategoryEntity, Long> {

    Optional<BusinessCategoryEntity> findById(Long id);

    List<BusinessCategoryEntity> findAllByTitleContains(String titleValue);
}
