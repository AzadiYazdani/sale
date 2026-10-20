package com.haraji.baseinfo.service.location.city;

import com.haraji.baseinfo.api.location.CityResponse;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface CityService {

    List<CityResponse> getByProvinceId(@Min(1) Long provinceId);

}
