package com.haraji.baseinfo.mapper.business;


import com.haraji.baseinfo.api.businessUnion.BusinessUnionResponse;
import com.haraji.baseinfo.database.entity.business.BusinessUnionEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface BusinessUnionMapper {

    List<BusinessUnionResponse> toDtoResponseList(List<BusinessUnionEntity> entities);
    
    BusinessUnionResponse toDtoResponse(BusinessUnionEntity entity);

}
