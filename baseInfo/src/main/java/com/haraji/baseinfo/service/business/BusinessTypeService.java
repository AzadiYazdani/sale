package com.haraji.baseinfo.service.business;


import com.haraji.baseinfo.model.BusinessType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface BusinessTypeService {

    BusinessType getById(@Min(1) Integer id);

    Page<BusinessType> getAllByPaging(@Valid @NotNull Pageable pageable);
    
    List<BusinessType> getAll();

    List<BusinessType> searchTitle(@NotNull String titleValue);

}
