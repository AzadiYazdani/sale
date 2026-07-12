package com.haraji.baseinfo.database.repository.business;

import com.haraji.baseinfo.database.entity.business.BusinessTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessTypeRepository extends JpaRepository<BusinessTypeEntity, Integer> {

    Optional<BusinessTypeEntity> findById(Integer id);

    List<BusinessTypeEntity> findAllByTitleContains(String titleValue);
}
