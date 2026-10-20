
package com.haraji.baseinfo.service.business.businesscategory;

import com.haraji.baseinfo.api.businessCategory.BusinessCategoryRequest;
import com.haraji.baseinfo.api.businessCategory.BusinessCategoryResponse;
import com.haraji.baseinfo.database.entity.business.BusinessCategoryEntity;
import com.haraji.baseinfo.database.repository.business.BusinessCategoryRepository;
import com.haraji.baseinfo.mapper.business.BusinessCategoryMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BusinessCategoryServiceImpl implements BusinessCategoryService {

    private final BusinessCategoryRepository businessCategoryRepository;
    private final BusinessCategoryMapper businessCategoryMapper;

    @Override
    public BusinessCategoryResponse getById(@Min(1) Long id) {
        return businessCategoryMapper.toResponse(findById(id));
    }

    @Override
    public List<BusinessCategoryResponse> getAll() {
        return businessCategoryMapper.toResponseList(businessCategoryRepository.findAll());
    }

    @Override
    public Page<BusinessCategoryResponse> getAllByPaging(@NotNull Pageable pageable) {
        return businessCategoryRepository.findAll(pageable).map(businessCategoryMapper::toResponse);
    }

    @Override
    public List<BusinessCategoryResponse> getByParentId(@Min(1) Long parentId) {
        return businessCategoryMapper.toResponseList(businessCategoryRepository.findAllByParentId(parentId));
    }

    @Override
    public List<BusinessCategoryResponse> searchTitle(@NotBlank String title) {
        return businessCategoryMapper.toResponseList(businessCategoryRepository.findAllByTitleContainingIgnoreCase(title));
    }

    @Override
    @Transactional
    public BusinessCategoryResponse create(@Valid @NotNull BusinessCategoryRequest request) {
        BusinessCategoryEntity entity = businessCategoryMapper.toEntity(request);
        return businessCategoryMapper.toResponse(businessCategoryRepository.save(entity));
    }

    @Override
    @Transactional
    public BusinessCategoryResponse update(@Min(1) Long id, @Valid @NotNull BusinessCategoryRequest request) {
        BusinessCategoryEntity entity = findById(id);
        businessCategoryMapper.updateEntity(request, entity);
        return businessCategoryMapper.toResponse(businessCategoryRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(@Min(1) Long id) {
        businessCategoryRepository.delete(findById(id));
    }

    private BusinessCategoryEntity findById(Long id) {
        return businessCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("رسته واحد صنفی با شناسه " + id + " یافت نشد."));
    }
}
