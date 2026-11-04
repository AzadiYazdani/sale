
package com.haraji.baseinfo.database.repository.business;

import com.haraji.baseinfo.database.entity.business.BusinessCategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessCategoryRepository extends JpaRepository<BusinessCategoryEntity, Long> {

    List<BusinessCategoryEntity> findAllByBusinessUnion_Id(Long businessUnionId);

    Page<BusinessCategoryEntity> findAllByBusinessUnion_Id(Long businessUnionId, Pageable pageable);

    List<BusinessCategoryEntity> findAllByTitleContainingIgnoreCase(String title);

    long countByBusinessUnion_Id(Long businessUnionId);
}
