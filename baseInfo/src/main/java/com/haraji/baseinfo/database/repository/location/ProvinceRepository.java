package com.haraji.baseinfo.database.repository.location;

import com.haraji.baseinfo.database.entity.location.ProvinceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepository extends JpaRepository<ProvinceEntity, Long> {

    List<ProvinceEntity> findAllByNameContainingIgnoreCase(String name);
}
