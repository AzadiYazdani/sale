
package com.haraji.baseinfo.service.business.businessunion;

import com.haraji.baseinfo.api.businessUnion.BusinessUnionRequest;
import com.haraji.baseinfo.api.businessUnion.BusinessUnionResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface BusinessUnionService {

    BusinessUnionResponse getById(@Min(1) Long id);

    List<BusinessUnionResponse> getAll();

    Page<BusinessUnionResponse> getAllByPaging(@NotNull Pageable pageable);

    List<BusinessUnionResponse> getByCityId(@Min(1) Long cityId);

    Page<BusinessUnionResponse> getByCityIdPaging(@Min(1) Long cityId, @NotNull Pageable pageable);

    List<BusinessUnionResponse> searchTitle(@NotBlank String title);

    BusinessUnionResponse create(@Valid @NotNull BusinessUnionRequest request);

    BusinessUnionResponse update(@Min(1) Long id, @Valid @NotNull BusinessUnionRequest request);

    void delete(@Min(1) Long id);
}
