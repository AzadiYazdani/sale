package com.haraji.baseinfo.mapper;


import com.haraji.baseinfo.api.businessType.BusinessTypeResponseDto;
import com.haraji.baseinfo.database.entity.business.BusinessTypeEntity;
import com.haraji.baseinfo.model.BusinessType;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface BusinessTypeMapper {

    BusinessType toModel(BusinessTypeEntity entity);

    List<BusinessType> toModelList(List<BusinessTypeEntity> businessTypeEntityList);

    List<BusinessTypeResponseDto> toDtoResponseList(List<BusinessType> businessTypeList);
    
    BusinessTypeResponseDto toDtoResponse(BusinessType businessTypeList);

}
