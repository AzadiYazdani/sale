package com.haraji.baseinfo.service.business;


import com.haraji.baseinfo.api.activityType.ActivityTypeResponse;
import com.haraji.baseinfo.database.entity.business.ActivityTypeEntity;
import com.haraji.baseinfo.database.repository.business.ActivityTypeRepository;
import com.haraji.baseinfo.mapper.business.ActivityTypeMapper;
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
public class ActivityTypeServiceImpl implements ActivityTypeService {

    private final ActivityTypeRepository activityTypeRepository;
    private final ActivityTypeMapper activityTypeMapper;

    public ActivityTypeServiceImpl(ActivityTypeRepository activityTypeRepository, ActivityTypeMapper activityTypeMapper) {
        this.activityTypeRepository = activityTypeRepository;
        this.activityTypeMapper = activityTypeMapper;
    }


    @Override
    public ActivityTypeResponse getById(@Min(1) Long id) {
        try {
            ActivityTypeEntity entity = activityTypeRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(EntityType.ACTIVITY_TYPE, id));
            return activityTypeMapper.toDtoResponse(entity);
        } catch (Exception e) {
            log.info("\nThe exception '{}' was thrown for ActivityTypeService.getById({})", e.getMessage(), id);
            throw new EntityNotFoundException(EntityType.ACTIVITY_TYPE, id);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActivityTypeResponse> getAllByPaging(@Valid @NotNull Pageable pageable) {
        try {
            Page<ActivityTypeEntity> activityTypeEntityPage = activityTypeRepository.findAll(pageable);
            if (activityTypeEntityPage.isEmpty() && pageable.getPageNumber() == 0) {
                throw new EntityNotFoundException(EntityType.ACTIVITY_TYPE);
            }

            return activityTypeEntityPage.map(activityTypeMapper::toDtoResponse);
        } catch (Exception e) {
            log.info("\nThe exception '{}' was thrown for ActivityTypeService.getAllByPaging({}) ", e.getMessage(), pageable);
            throw new EntityNotFoundException(EntityType.ACTIVITY_TYPE);
        }
    }

    @Override
    public List<ActivityTypeResponse> getAll() {
        try {
            List<ActivityTypeEntity> entities = activityTypeRepository.findAll();
            if (!entities.isEmpty())
                return activityTypeMapper.toDtoResponseList(entities);
            return null;
        } catch (Exception e) {
            log.info("\nThe exception '{}' was thrown for ActivityTypeService.getAll()", e.getMessage());
            throw new EntityNotFoundException(EntityType.ACTIVITY_TYPE);
        }
    }

    @Override
    public List<ActivityTypeResponse> searchTitle(@NotNull String titleValue) {
        if (StringUtils.isBlank(titleValue))
            throw new BadRequestException();

        List<ActivityTypeEntity> businessTypeEntityList = activityTypeRepository.findAllByTitleContains(titleValue);
        return activityTypeMapper.toDtoResponseList(businessTypeEntityList);
    }
}
