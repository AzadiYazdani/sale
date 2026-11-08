package com.haraji.baseinfo.database.repository.location;

import com.haraji.baseinfo.database.entity.location.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {

    List<CityEntity> findAllByProvinceId(Long provinceId);
}
