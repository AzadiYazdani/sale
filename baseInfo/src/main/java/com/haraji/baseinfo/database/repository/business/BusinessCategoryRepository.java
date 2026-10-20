package com.haraji.baseinfo.database.repository.business;

import com.haraji.baseinfo.database.entity.business.BusinessCategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessCategoryRepository extends JpaRepository<BusinessCategoryEntity, Long> {

    List<BusinessCategoryEntity> findAllByParentId(Long parentId);

    List<BusinessCategoryEntity> findAllByTitleContainingIgnoreCase(String title);

    Page<BusinessCategoryEntity> findAllBy(Pageable pageable);
}
