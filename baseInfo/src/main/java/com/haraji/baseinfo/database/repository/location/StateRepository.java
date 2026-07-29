package com.haraji.baseinfo.database.repository.location;

import com.haraji.baseinfo.database.entity.location.ProvinceEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StateRepository extends PagingAndSortingRepository<ProvinceEntity, Integer> {

    Optional<ProvinceEntity> findById(int id);

    List<ProvinceEntity> findAllByTitleContains(String titleValue);

    List<ProvinceEntity> findAll();
}
