package com.haraji.baseinfo.service.location.city;


import com.haraji.baseinfo.database.entity.location.CityEntity;
import com.haraji.baseinfo.database.repository.location.CityRepository;
import com.haraji.baseinfo.mapper.location.CityMapper;
import com.haraji.baseinfo.model.location.City;
import com.haraji.common.constant.EntityType;
import com.haraji.common.exception.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CityServiceImpl implements CityService {


    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Override
    public City getById(@Min(1) int id) {
        try {
            CityEntity cityEntity = cityRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(EntityType.CITY, id));
            return cityMapper.toModel(cityEntity);
        } catch (Exception e) {
            log.info("\nThe exception '{}' was thrown for CityService.getById({})", e.getMessage(), id);
            throw new EntityNotFoundException(EntityType.CITY, id);
        }
    }

    @Override
    public Page<City> getAllByPaging(@Valid @NotNull Pageable pageable) {
        try {
//            Page<CityEntity> cityEntityPage = cityRepository.findAll(pageable);
//
//            if (cityEntityPage.isEmpty()) {
//                throw new CityNotFoundException();
//            }
            List<City> cityList = new ArrayList<>();

//            cityEntityPage.forEach(cityEntity -> cityList.add(cityMapper.toModel(cityEntity)));
            return new PageImpl<>(cityList);
        } catch (Exception e) {
            log.info("\nThe exception '{}' was thrown for CityService.getAllByPaging({}) ", e.getMessage(), pageable);
            throw new EntityNotFoundException(EntityType.CITY);
        }
    }
}
