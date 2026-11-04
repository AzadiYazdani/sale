
package com.haraji.baseinfo.mapper.business;

import com.haraji.baseinfo.api.businessCategory.BusinessCategoryRequest;
import com.haraji.baseinfo.api.businessCategory.BusinessCategoryResponse;
import com.haraji.baseinfo.database.entity.business.BusinessCategoryEntity;
import com.haraji.baseinfo.database.entity.business.BusinessUnionEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface BusinessCategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "businessUnion", source = "businessUnionId")
    BusinessCategoryEntity toEntity(BusinessCategoryRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "businessUnion", source = "businessUnionId")
    void updateEntity(BusinessCategoryRequest request, @MappingTarget BusinessCategoryEntity entity);

    @Mapping(target = "businessUnionId", source = "businessUnion.id")
    BusinessCategoryResponse toDtoResponse(BusinessCategoryEntity entity);

    List<BusinessCategoryResponse> toDtoResponseList(List<BusinessCategoryEntity> entities);

    default BusinessUnionEntity map(Long businessUnionId) {
        if (businessUnionId == null) {
            return null;
        }

        return BusinessUnionEntity.builder().setId(businessUnionId).build();
    }
}
