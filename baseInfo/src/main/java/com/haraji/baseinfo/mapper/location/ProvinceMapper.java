package com.haraji.baseinfo.mapper.location;


import com.haraji.baseinfo.api.location.province.ProvinceResponseDto;
import com.haraji.baseinfo.database.entity.location.ProvinceEntity;
import com.haraji.baseinfo.model.location.Province;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ProvinceMapper {


    Province toModel(ProvinceEntity entity);

    List<Province> toModelList(List<ProvinceEntity> provinceEntityList);

    ProvinceResponseDto toDtoResponse(Province entity);

    List<ProvinceResponseDto> toDtoResponseList(List<Province> provinceList);

}
