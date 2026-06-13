package com.haraji.baseinfo.service.location.city;


import com.haraji.baseinfo.model.location.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public interface CityService {

    City getById(@Min(1) int id);

    Page<City> getAllByPaging(@Valid @NotNull Pageable pageable);

}
