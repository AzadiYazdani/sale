package com.haraji.baseinfo.service.business;


import com.haraji.baseinfo.api.activityType.ActivityTypeResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface ActivityTypeService {

    ActivityTypeResponse getById(@Min(1) Long id);

    Page<ActivityTypeResponse> getAllByPaging(@Valid @NotNull Pageable pageable);
    
    List<ActivityTypeResponse> getAll();

    List<ActivityTypeResponse> searchTitle(@NotNull String titleValue);

}
