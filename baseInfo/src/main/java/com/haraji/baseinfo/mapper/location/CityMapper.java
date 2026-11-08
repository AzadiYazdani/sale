package com.haraji.baseinfo.mapper.location;

import com.haraji.baseinfo.api.location.CityResponse;
import com.haraji.baseinfo.database.entity.location.CityEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface CityMapper {

    @Mapping(target = "provinceId", source = "province.id")
    CityResponse toResponse(CityEntity entity);

    List<CityResponse> toResponseList(List<CityEntity> cityEntityList);
}
