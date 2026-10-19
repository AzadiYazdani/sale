package com.haraji.baseinfo.service.business;


import com.haraji.baseinfo.api.businessUnion.BusinessUnionResponse;
import com.haraji.baseinfo.database.entity.business.BusinessUnionEntity;
import com.haraji.baseinfo.database.repository.business.BusinessUnionRepository;
import com.haraji.baseinfo.mapper.business.BusinessUnionMapper;
import com.haraji.common.constant.EntityType;
import com.haraji.common.exception.BadRequestException;
import com.haraji.common.exception.EntityNotFoundException;
import com.haraji.common.logging.LogLevel;
import com.haraji.common.logging.MethodLogging;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Slf4j
@Validated
@MethodLogging(level = LogLevel.DEBUG)
public class BusinessUnionServiceImpl implements BusinessUnionService {

    private final BusinessUnionRepository businessUnionRepository;
    private final BusinessUnionMapper businessUnionMapper;

    public BusinessUnionServiceImpl(BusinessUnionRepository businessUnionRepository, BusinessUnionMapper businessUnionMapper) {
        this.businessUnionRepository = businessUnionRepository;
        this.businessUnionMapper = businessUnionMapper;
    }


    @Override
    public BusinessUnionResponse getById(@Min(1) Long id) {
        try {
            BusinessUnionEntity entity = businessUnionRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(EntityType.ACTIVITY_TYPE, id));
            return businessUnionMapper.toDtoResponse(entity);
        } catch (Exception e) {
            log.info("\nThe exception '{}' was thrown for BisinessUnionService.getById({})", e.getMessage(), id);
            throw new EntityNotFoundException(EntityType.ACTIVITY_TYPE, id);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BusinessUnionResponse> getAllByPaging(@Valid @NotNull Pageable pageable) {
        try {
            Page<BusinessUnionEntity> businessUnionEntityPage = businessUnionRepository.findAll(pageable);
            if (businessUnionEntityPage.isEmpty() && pageable.getPageNumber() == 0) {
                throw new EntityNotFoundException(EntityType.ACTIVITY_TYPE);
            }

            return businessUnionEntityPage.map(businessUnionMapper::toDtoResponse);
        } catch (Exception e) {
            log.info("\nThe exception '{}' was thrown for BisinessUnionService.getAllByPaging({}) ", e.getMessage(), pageable);
            throw new EntityNotFoundException(EntityType.ACTIVITY_TYPE);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<BusinessUnionResponse> getByCityId(@Min(1) Long cityId) {
        try {
            List<BusinessUnionEntity> entities = businessUnionRepository.findAllByCityId(cityId);

            if (entities.isEmpty()) {
                throw new EntityNotFoundException(EntityType.BUSINESS_UNION);
            }
            return businessUnionMapper.toDtoResponseList(entities);

        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("The exception '{}' was thrown for BusinessUnionService.getByCityId({})",
                    e.getMessage(), cityId, e
            );
            throw new EntityNotFoundException(EntityType.BUSINESS_UNION);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BusinessUnionResponse> getByCityIdPaging(
            @Min(1) Long cityId,
            @Valid @NotNull Pageable pageable) {

        try {
            Page<BusinessUnionEntity> entityPage = businessUnionRepository.findAllByCityId(cityId, pageable);
            if (entityPage.isEmpty() && pageable.getPageNumber() == 0) {
                throw new EntityNotFoundException(EntityType.BUSINESS_UNION);
            }

            return entityPage.map(businessUnionMapper::toDtoResponse);

        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error(
                    "The exception '{}' was thrown for BusinessUnionService.getByCityIdPaging({}, {})",
                    e.getMessage(), cityId, pageable, e
            );
            throw new EntityNotFoundException(EntityType.BUSINESS_UNION);
        }
    }

    @Override
    public List<BusinessUnionResponse> getAll() {
        try {
            List<BusinessUnionEntity> entities = businessUnionRepository.findAll();
            if (!entities.isEmpty())
                return businessUnionMapper.toDtoResponseList(entities);
            return null;
        } catch (Exception e) {
            log.info("\nThe exception '{}' was thrown for BisinessUnionService.getAll()", e.getMessage());
            throw new EntityNotFoundException(EntityType.ACTIVITY_TYPE);
        }
    }

    @Override
    public List<BusinessUnionResponse> searchTitle(@NotNull String titleValue) {
        if (StringUtils.isBlank(titleValue))
            throw new BadRequestException();

        List<BusinessUnionEntity> businessTypeEntityList = businessUnionRepository.findAllByTitleContains(titleValue);
        return businessUnionMapper.toDtoResponseList(businessTypeEntityList);
    }
}
