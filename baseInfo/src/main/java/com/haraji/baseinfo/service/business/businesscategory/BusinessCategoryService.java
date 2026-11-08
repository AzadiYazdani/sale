
package com.haraji.baseinfo.service.business.businesscategory;


import com.haraji.baseinfo.api.businessCategory.BusinessCategoryRequest;
import com.haraji.baseinfo.api.businessCategory.BusinessCategoryResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface BusinessCategoryService {

    BusinessCategoryResponse getById(@Min(1) Long id);

    Page<BusinessCategoryResponse> getAllByPaging(@NotNull Pageable pageable);

    List<BusinessCategoryResponse> getAll();

    List<BusinessCategoryResponse> searchTitle(@NotBlank String title);

    List<BusinessCategoryResponse> getAllByBusinessUnionId(@Min(1) Long businessUnionId);

    BusinessCategoryResponse create(@Valid @NotNull BusinessCategoryRequest request);

    BusinessCategoryResponse update(@Min(1) Long id, @Valid @NotNull BusinessCategoryRequest request);

    void delete(@Min(1) Long id);
}
