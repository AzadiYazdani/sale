package com.haraji.baseinfo.database.repository.business;

import com.haraji.baseinfo.database.entity.business.BusinessTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessTypeRepository extends JpaRepository<BusinessTypeEntity, Long> {

    Optional<BusinessTypeEntity> findById(Long id);

    List<BusinessTypeEntity> findAllByTitleContains(String titleValue);
}
