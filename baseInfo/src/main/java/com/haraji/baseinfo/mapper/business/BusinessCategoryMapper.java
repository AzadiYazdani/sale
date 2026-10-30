package com.haraji.baseinfo.mapper.business;

import com.haraji.baseinfo.api.businessCategory.BusinessCategoryRequest;
import com.haraji.baseinfo.api.businessCategory.BusinessCategoryResponse;
import com.haraji.baseinfo.database.entity.business.BusinessCategoryEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface BusinessCategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parentId", source = "businessCategoryId")
    BusinessCategoryEntity toEntity(BusinessCategoryRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parentId", source = "businessCategoryId")
    void updateEntity(BusinessCategoryRequest request, @MappingTarget BusinessCategoryEntity entity);

    @Mapping(target = "parentId", source = "parentId")
    BusinessCategoryResponse toResponse(BusinessCategoryEntity entity);

    List<BusinessCategoryResponse> toResponseList(List<BusinessCategoryEntity> entities);
}
