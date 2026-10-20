package com.haraji.baseinfo.mapper.location;


import com.haraji.baseinfo.api.location.ProvinceResponse;
import com.haraji.baseinfo.database.entity.location.ProvinceEntity;
import com.haraji.baseinfo.model.location.Province;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ProvinceMapper {


    List<ProvinceResponse> toResponseList(List<ProvinceEntity> entities);

    ProvinceResponse toResponse(ProvinceEntity entity);
}
