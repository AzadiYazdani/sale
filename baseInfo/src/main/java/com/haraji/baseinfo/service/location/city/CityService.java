package com.haraji.baseinfo.service.location.city;


import com.haraji.baseinfo.model.location.City;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CityService {

    City getById(@Min(1) Long id);

    Page<City> getAllByPaging(@Valid @NotNull Pageable pageable);

}
