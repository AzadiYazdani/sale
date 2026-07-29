package com.haraji.baseinfo.database.repository.location;

import com.haraji.baseinfo.database.entity.location.ProvinceEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProvinceRepository extends PagingAndSortingRepository<ProvinceEntity, Integer> {

    Optional<ProvinceEntity> findById(int id);

    List<ProvinceEntity> findAllByNameContains(String value);

    List<ProvinceEntity> findAll();
}
