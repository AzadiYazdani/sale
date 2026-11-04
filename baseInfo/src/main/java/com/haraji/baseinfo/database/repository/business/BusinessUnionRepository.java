
package com.haraji.baseinfo.database.repository.business;

import com.haraji.baseinfo.database.entity.business.BusinessUnionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessUnionRepository extends JpaRepository<BusinessUnionEntity, Long> {

    List<BusinessUnionEntity> findAllByCity_Id(Long cityId);

    Page<BusinessUnionEntity> findAllByCity_Id(Long cityId, Pageable pageable);

    List<BusinessUnionEntity> findAllByTitleContainingIgnoreCase(String title);

}
