package com.haraji.baseinfo.service.location.province;


import com.haraji.baseinfo.model.location.City;
import com.haraji.baseinfo.model.location.Province;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface ProvinceService {

    Province getById(@Min(1) Long id);

    List<Province> getAll();

    Page<Province> getAllByPaging(@Valid @NotNull Pageable pageable);

    List<City> getAllCitiesById(@Min(1) Long provinceId);

    List<Province> searchTitle(@NotNull String title);

}
