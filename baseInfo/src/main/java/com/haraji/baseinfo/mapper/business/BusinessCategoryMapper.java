package com.haraji.baseinfo.mapper.business;


import com.haraji.baseinfo.api.businessCategory.BusinessCategoryResponse;
import com.haraji.baseinfo.database.entity.business.BusinessCategoryEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface BusinessCategoryMapper {

    List<BusinessCategoryResponse> toDtoResponseList(List<BusinessCategoryEntity> entities);
    
    BusinessCategoryResponse toDtoResponse(BusinessCategoryEntity entity);

}
