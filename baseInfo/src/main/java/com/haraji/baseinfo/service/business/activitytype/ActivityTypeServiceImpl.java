
package com.haraji.baseinfo.service.business.activitytype;

import com.haraji.baseinfo.api.activityType.ActivityTypeRequest;
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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Slf4j
@Validated
@RequiredArgsConstructor
@MethodLogging(level = LogLevel.DEBUG)
public class ActivityTypeServiceImpl implements ActivityTypeService {

    private final ActivityTypeRepository activityTypeRepository;
    private final ActivityTypeMapper activityTypeMapper;

    @Override
    @Transactional(readOnly = true)
    public ActivityTypeResponse getById(@Min(1) Long id) {
        ActivityTypeEntity entity = activityTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EntityType.ACTIVITY_TYPE, id));
        return activityTypeMapper.toDtoResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActivityTypeResponse> getAllByPaging(@NotNull Pageable pageable) {
        return activityTypeRepository.findAll(pageable).map(activityTypeMapper::toDtoResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityTypeResponse> getAll() {
        return activityTypeMapper.toDtoResponseList(activityTypeRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityTypeResponse> searchTitle(@NotBlank String title) {
        if (title == null || title.isBlank()) {
            throw new BadRequestException();
        }
        return activityTypeMapper.toDtoResponseList(activityTypeRepository.findAllByTitleContains(title));
    }

    @Override
    @Transactional
    public ActivityTypeResponse create(@Valid @NotNull ActivityTypeRequest request) {
        ActivityTypeEntity entity = activityTypeMapper.toEntity(request);
        return activityTypeMapper.toDtoResponse(activityTypeRepository.save(entity));
    }

    @Override
    @Transactional
    public ActivityTypeResponse update(@Min(1) Long id, @Valid @NotNull ActivityTypeRequest request) {
        ActivityTypeEntity entity = activityTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EntityType.ACTIVITY_TYPE, id));

        activityTypeMapper.updateEntity(request, entity);
        return activityTypeMapper.toDtoResponse(activityTypeRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(@Min(1) Long id) {
        ActivityTypeEntity entity = activityTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EntityType.ACTIVITY_TYPE, id));
        activityTypeRepository.delete(entity);
    }
}
