package com.haraji.baseinfo.service.business;


import com.haraji.baseinfo.database.entity.business.BusinessTypeEntity;
import com.haraji.baseinfo.database.repository.business.BusinessTypeRepository;
import com.haraji.baseinfo.exception.BusinessTypeNotFoundException;
import com.haraji.baseinfo.mapper.BusinessTypeMapper;
import com.haraji.baseinfo.model.BusinessType;
import com.haraji.common.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Validated
public class BusinessTypeServiceImpl implements BusinessTypeService {

    private final BusinessTypeRepository businessTypeRepository;
    private final BusinessTypeMapper businessTypeMapper;

    public BusinessTypeServiceImpl(BusinessTypeRepository businessTypeRepository, BusinessTypeMapper businessTypeMapper) {
        this.businessTypeRepository = businessTypeRepository;
        this.businessTypeMapper = businessTypeMapper;
    }


    @Override
    public BusinessType getById(@Min(1) long id) {
        try {
            BusinessTypeEntity businessTypeEntity = businessTypeRepository.findById(id)
                    .orElseThrow(() -> new BusinessTypeNotFoundException(id));
            return businessTypeMapper.toModel(businessTypeEntity);
        } catch (Exception e) {
            log.info("\nThe exception '{}' was thrown for ActivityTypeService.getById({})", e.getMessage(), id);
            throw new BusinessTypeNotFoundException(id);
        }
    }

    @Override
    public Page<BusinessType> getAllByPaging(@Valid @NotNull Pageable pageable) {
        try {
            Page<BusinessTypeEntity> activityTypeEntityPage = businessTypeRepository.findAll(pageable);

            if (activityTypeEntityPage.isEmpty()) {
                throw new BusinessTypeNotFoundException();
            }
            List<BusinessType> businessTypeList = new ArrayList<>();
            activityTypeEntityPage.forEach(businessTypeEntity -> businessTypeList.add(businessTypeMapper.toModel(businessTypeEntity)));
            return new PageImpl<>(businessTypeList);
        } catch (Exception e) {
            log.info("\nThe exception '{}' was thrown for ActivityTypeService.getAllByPaging({}) ", e.getMessage(), pageable);
            throw new BusinessTypeNotFoundException();
        }
    }

    @Override
    public List<BusinessType> getAll() {
        try {
            List<BusinessTypeEntity> businessTypeEntityList = businessTypeRepository.findAll();
            if (businessTypeEntityList != null && !businessTypeEntityList.isEmpty())
                return businessTypeMapper.toModelList(businessTypeEntityList);
            return null;
        } catch (Exception e) {
            log.info("\nThe exception '{}' was thrown for BusinessTypeService.getAll()", e.getMessage());
            throw new BusinessTypeNotFoundException();
        }
    }

    @Override
    public List<BusinessType> searchTitle(@NotNull String titleValue) {
        if (StringUtils.isBlank(titleValue))
            throw new BadRequestException();

        List<BusinessTypeEntity> businessTypeEntityList = businessTypeRepository.findAllByTitleContains(titleValue);
        return businessTypeMapper.toModelList(businessTypeEntityList);
    }
}
