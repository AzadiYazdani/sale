package com.haraji.baseinfo.service.business.businesscategory;

import com.haraji.baseinfo.api.businessCategory.BusinessCategoryRequest;
import com.haraji.baseinfo.api.businessCategory.BusinessCategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BusinessCategoryService {
    BusinessCategoryResponse getById(Long id);

    List<BusinessCategoryResponse> getAll();

    Page<BusinessCategoryResponse> getAllByPaging(Pageable pageable);

    List<BusinessCategoryResponse> getByParentId(Long parentId);

    List<BusinessCategoryResponse> searchTitle(String title);

    BusinessCategoryResponse create(BusinessCategoryRequest request);

    BusinessCategoryResponse update(Long id, BusinessCategoryRequest request);

    void delete(Long id);
}
