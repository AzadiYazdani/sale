
package com.haraji.baseinfo.service.business.businessunion;

import com.haraji.baseinfo.api.businessUnion.BusinessUnionRequest;
import com.haraji.baseinfo.api.businessUnion.BusinessUnionResponse;
import com.haraji.baseinfo.database.entity.business.BusinessUnionEntity;
import com.haraji.baseinfo.database.repository.business.BusinessCategoryRepository;
import com.haraji.baseinfo.database.repository.business.BusinessUnionRepository;
import com.haraji.baseinfo.database.repository.location.CityRepository;
import com.haraji.baseinfo.mapper.business.BusinessUnionMapper;
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
public class BusinessUnionServiceImpl implements BusinessUnionService {

    private final BusinessUnionRepository businessUnionRepository;
    private final BusinessCategoryRepository businessCategoryRepository;
    private final CityRepository cityRepository;
    private final BusinessUnionMapper businessUnionMapper;

    @Override
    @Transactional(readOnly = true)
    public BusinessUnionResponse getById(@Min(1) Long id) {
        BusinessUnionEntity entity = businessUnionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EntityType.BUSINESS_UNION, id));

        return businessUnionMapper.toDtoResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BusinessUnionResponse> getAllByPaging(@NotNull Pageable pageable) {
        return businessUnionRepository.findAll(pageable)
                .map(businessUnionMapper::toDtoResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BusinessUnionResponse> getAll() {
        return businessUnionMapper.toDtoResponseList(businessUnionRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BusinessUnionResponse> searchTitle(@NotBlank String title) {
        if (title == null || title.isBlank()) {
            throw new BadRequestException();
        }

        return businessUnionMapper.toDtoResponseList(
                businessUnionRepository.findAllByTitleContainingIgnoreCase(title.trim())
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<BusinessUnionResponse> getAllByCityId(@Min(1) Long cityId) {
        return businessUnionMapper.toDtoResponseList(
                businessUnionRepository.findAllByCity_Id(cityId)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BusinessUnionResponse> getAllByCityIdPaging(
            @Min(1) Long cityId,
            @NotNull Pageable pageable
    ) {
        return businessUnionRepository.findAllByCity_Id(cityId, pageable)
                .map(businessUnionMapper::toDtoResponse);
    }

    @Override
    @Transactional
    public BusinessUnionResponse create(@Valid @NotNull BusinessUnionRequest request) {
        validateCity(request.getCityId());

        BusinessUnionEntity entity = businessUnionMapper.toEntity(request);

        return businessUnionMapper.toDtoResponse(
                businessUnionRepository.save(entity)
        );
    }

    @Override
    @Transactional
    public BusinessUnionResponse update(
            @Min(1) Long id,
            @Valid @NotNull BusinessUnionRequest request
    ) {
        BusinessUnionEntity entity = businessUnionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EntityType.BUSINESS_UNION, id));

        validateCity(request.getCityId());

        businessUnionMapper.updateEntity(request, entity);

        return businessUnionMapper.toDtoResponse(
                businessUnionRepository.save(entity)
        );
    }

    @Override
    @Transactional
    public void delete(@Min(1) Long id) {
        BusinessUnionEntity entity = businessUnionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EntityType.BUSINESS_UNION, id));

        if (businessCategoryRepository.countByBusinessUnion_Id(id) > 0) {
            throw new BadRequestException();
        }

        businessUnionRepository.delete(entity);
    }

    private void validateCity(Long cityId) {
        if (!cityRepository.existsById(cityId)) {
            throw new EntityNotFoundException(EntityType.CITY, cityId);
        }
    }
}
