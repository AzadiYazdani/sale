package com.haraji.baseinfo.database.repository.business;

import com.haraji.baseinfo.database.entity.business.BusinessUnionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BusinessUnionRepository extends JpaRepository<BusinessUnionEntity, Long> {

    List<BusinessUnionEntity> findAllByCity_Id(Long cityId);

    Page<BusinessUnionEntity> findAllByCity_Id(Long cityId, Pageable pageable);

    List<BusinessUnionEntity> findAllByTitleContainingIgnoreCase(String title);

    @Query("""
            SELECT businessUnion
            FROM BusinessUnionEntity businessUnion
            WHERE (:provinceId IS NULL OR businessUnion.city.province.id = :provinceId)
              AND (:cityId IS NULL OR businessUnion.city.id = :cityId)
              AND (
                    :title IS NULL
                    OR LOWER(businessUnion.title) LIKE LOWER(CONCAT('%', :title, '%'))
                  )
            """)
    Page<BusinessUnionEntity> findAllByFilters(
            @Param("provinceId") Long provinceId,
            @Param("cityId") Long cityId,
            @Param("title") String title,
            Pageable pageable
    );
}
