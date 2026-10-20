package com.haraji.baseinfo.service.business;

import com.haraji.baseinfo.api.businessCategory.BusinessCategoryResponse;
import com.haraji.baseinfo.database.entity.business.BusinessCategoryEntity;
import com.haraji.baseinfo.database.repository.business.BusinessCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BusinessCategoryServiceImpl implements BusinessCategoryService {

    private final BusinessCategoryRepository businessCategoryRepository;

    public BusinessCategoryServiceImpl(BusinessCategoryRepository businessCategoryRepository) {
        this.businessCategoryRepository = businessCategoryRepository;
    }

    @Override
    public BusinessCategoryResponse getById(Long id) {
        BusinessCategoryEntity entity = businessCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("دسته‌بندی کسب‌وکار با شناسه " + id + " یافت نشد."));
        return toResponse(entity);
    }

    @Override
    public List<BusinessCategoryResponse> getAll() {
        return businessCategoryRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public Page<BusinessCategoryResponse> getAllByPaging(Pageable pageable) {
        return businessCategoryRepository.findAll(pageable).map(this::toResponse);
    }

    @Override
    public List<BusinessCategoryResponse> getByParentId(Long parentId) {
        return businessCategoryRepository.findAllByParentId(parentId).stream().map(this::toResponse).toList();
    }

    @Override
    public List<BusinessCategoryResponse> searchTitle(String title) {
        return businessCategoryRepository.findAllByTitleContainingIgnoreCase(title).stream().map(this::toResponse).toList();
    }

    private BusinessCategoryResponse toResponse(BusinessCategoryEntity entity) {
        return BusinessCategoryResponse.newInstance()
                .setId(entity.getId())
                .setParentId(entity.getParentId())
                .setTitle(entity.getTitle())
                .build();
    }
}
