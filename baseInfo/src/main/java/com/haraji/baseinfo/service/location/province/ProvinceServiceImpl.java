package com.haraji.baseinfo.service.location.province;

import com.haraji.baseinfo.database.entity.location.CityEntity;
import com.haraji.baseinfo.database.entity.location.ProvinceEntity;
import com.haraji.baseinfo.database.repository.location.ProvinceRepository;
import com.haraji.baseinfo.mapper.location.CityMapper;
import com.haraji.baseinfo.mapper.location.ProvinceMapper;
import com.haraji.baseinfo.model.location.City;
import com.haraji.baseinfo.model.location.Province;
import com.haraji.common.constant.EntityType;
import com.haraji.common.exception.BadRequestException;
import com.haraji.common.exception.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Slf4j
@Validated
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceRepository provinceRepository;
    private final ProvinceMapper provinceMapper;
    private final CityMapper cityMapper;

    public ProvinceServiceImpl(ProvinceRepository provinceRepository, ProvinceMapper provinceMapper, CityMapper cityMapper) {
        this.provinceRepository = provinceRepository;
        this.provinceMapper = provinceMapper;
        this.cityMapper = cityMapper;
    }

    @Override
    public Province getById(@Min(1) Long id) {
        try {
            ProvinceEntity provinceEntity = provinceRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(EntityType.PROVINCE, id));
            return provinceMapper.toModel(provinceEntity);
        } catch (Exception e) {
            log.info("\nThe exception '{}' was thrown for ProvinceService.getById({})", e.getMessage(), id);
            throw new EntityNotFoundException(EntityType.PROVINCE, id);
        }
    }

    @Override
    public List<Province> getAll() {
        try {
            List<ProvinceEntity> provinceEntityList = provinceRepository.findAll();
            if (provinceEntityList != null && !provinceEntityList.isEmpty())
                return provinceMapper.toModelList(provinceEntityList);
            return null;
        } catch (Exception e) {
            log.info("\nThe exception '{}' was thrown for ProvinceService.getAll()", e.getMessage());
            throw new EntityNotFoundException(EntityType.PROVINCE);
        }
    }

    @Override
    public Page<Province> getAllByPaging(@Valid @NotNull Pageable pageable) {
        try {
            Page<ProvinceEntity> provinceEntityPage = provinceRepository.findAll(pageable);

            if (provinceEntityPage.isEmpty()) {
                throw new EntityNotFoundException(EntityType.PROVINCE);
            }
            List<ProvinceEntity> entities = provinceEntityPage.getContent();
            List<Province> provinceList = provinceMapper.toModelList(entities);
            return new PageImpl<>(provinceList);
        } catch (Exception e) {
            log.info("\nThe exception '{}' was thrown for ProvinceService.getAllByPaging({}) ", e.getMessage(), pageable);
            throw new EntityNotFoundException(EntityType.PROVINCE);
        }
    }

    @Override
    public List<City> getAllCitiesById(@Min(1) Long provinceId) {
        try {
            ProvinceEntity provinceEntity = provinceRepository.findById(provinceId)
                    .orElseThrow(() -> new EntityNotFoundException(EntityType.PROVINCE, provinceId));
            List<CityEntity> cityEntities = provinceEntity.getCities();

            return cityMapper.toModelList(cityEntities);
        } catch (Exception e) {
            log.info("\nThe exception '{}' was thrown for ProvinceService.getAllCitiesById({})", e.getMessage(), provinceId);
            throw new EntityNotFoundException(EntityType.PROVINCE, provinceId);
        }
    }

    @Override
    public List<Province> searchTitle(@NotNull String titleValue) {
        if (StringUtils.isBlank(titleValue))
            throw new BadRequestException();

        List<ProvinceEntity> provinceEntityList = provinceRepository.findAllByNameContains(titleValue);
        return provinceMapper.toModelList(provinceEntityList);
    }
}
