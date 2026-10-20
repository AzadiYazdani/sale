
package com.haraji.baseinfo.database.repository.business;

import com.haraji.baseinfo.database.entity.business.BusinessUnionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessUnionRepository extends JpaRepository<BusinessUnionEntity, Long> {

    List<BusinessUnionEntity> findAllByCityId(Long cityId);

    Page<BusinessUnionEntity> findAllByCityId(Long cityId, Pageable pageable);

    List<BusinessUnionEntity> findAllByTitleContainingIgnoreCase(String title);
}
