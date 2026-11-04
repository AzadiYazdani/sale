
package com.haraji.baseinfo.service.business.businesscategory;

import com.haraji.baseinfo.api.businessCategory.BusinessCategoryRequest;
import com.haraji.baseinfo.api.businessCategory.BusinessCategoryResponse;
import com.haraji.baseinfo.database.entity.business.BusinessCategoryEntity;
import com.haraji.baseinfo.database.repository.business.BusinessCategoryRepository;
import com.haraji.baseinfo.database.repository.business.BusinessUnionRepository;
import com.haraji.baseinfo.mapper.business.BusinessCategoryMapper;
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
public class BusinessCategoryServiceImpl implements BusinessCategoryService {

    private final BusinessCategoryRepository businessCategoryRepository;
    private final BusinessUnionRepository businessUnionRepository;
    private final BusinessCategoryMapper businessCategoryMapper;

    @Override
    @Transactional(readOnly = true)
    public BusinessCategoryResponse getById(@Min(1) Long id) {
        BusinessCategoryEntity entity = businessCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EntityType.BUSINESS_CATEGORY, id));

        return businessCategoryMapper.toDtoResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BusinessCategoryResponse> getAllByPaging(@NotNull Pageable pageable) {
        return businessCategoryRepository.findAll(pageable)
                .map(businessCategoryMapper::toDtoResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BusinessCategoryResponse> getAll() {
        return businessCategoryMapper.toDtoResponseList(businessCategoryRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BusinessCategoryResponse> searchTitle(@NotBlank String title) {
        if (title == null || title.isBlank()) {
            throw new BadRequestException();
        }

        return businessCategoryMapper.toDtoResponseList(
                businessCategoryRepository.findAllByTitleContainingIgnoreCase(title.trim())
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<BusinessCategoryResponse> getAllByBusinessUnionId(
            @Min(1) Long businessUnionId
    ) {
        return businessCategoryMapper.toDtoResponseList(
                businessCategoryRepository.findAllByBusinessUnion_Id(businessUnionId)
        );
    }

    @Override
    @Transactional
    public BusinessCategoryResponse create(
            @Valid @NotNull BusinessCategoryRequest request
    ) {
        validateBusinessUnion(request.getBusinessUnionId());

        BusinessCategoryEntity entity = businessCategoryMapper.toEntity(request);

        return businessCategoryMapper.toDtoResponse(
                businessCategoryRepository.save(entity)
        );
    }

    @Override
    @Transactional
    public BusinessCategoryResponse update(
            @Min(1) Long id,
            @Valid @NotNull BusinessCategoryRequest request
    ) {
        BusinessCategoryEntity entity = businessCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EntityType.BUSINESS_CATEGORY, id));

        validateBusinessUnion(request.getBusinessUnionId());

        businessCategoryMapper.updateEntity(request, entity);

        return businessCategoryMapper.toDtoResponse(
                businessCategoryRepository.save(entity)
        );
    }

    @Override
    @Transactional
    public void delete(@Min(1) Long id) {
        BusinessCategoryEntity entity = businessCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EntityType.BUSINESS_CATEGORY, id));

        businessCategoryRepository.delete(entity);
    }

    private void validateBusinessUnion(Long businessUnionId) {
        if (!businessUnionRepository.existsById(businessUnionId)) {
            throw new EntityNotFoundException(EntityType.BUSINESS_UNION, businessUnionId);
        }
    }
}
