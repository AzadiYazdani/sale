package com.haraji.baseinfo.mapper.location;


import com.haraji.baseinfo.api.location.city.CityResponseDto;
import com.haraji.baseinfo.database.entity.location.CityEntity;
import com.haraji.baseinfo.model.location.City;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface CityMapper {

    @Mapping(target = "provinceId", source = "entity.province.id")
    City toModel(CityEntity entity);

      List<City> toModelList(List<CityEntity> cityEntityList);

      List<CityResponseDto> toDtoResponseList(List<City> postList);

}
