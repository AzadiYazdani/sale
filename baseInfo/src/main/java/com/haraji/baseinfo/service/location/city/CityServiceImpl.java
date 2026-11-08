package com.haraji.baseinfo.service.location.city;

import com.haraji.baseinfo.api.location.CityResponse;
import com.haraji.baseinfo.database.entity.location.CityEntity;
import com.haraji.baseinfo.database.repository.location.CityRepository;
import com.haraji.baseinfo.mapper.location.CityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CityResponse> getByProvinceId(Long provinceId) {
        List<CityEntity> cities = cityRepository.findAllByProvinceId(provinceId);
        return cityMapper.toResponseList(cities);
    }
}
