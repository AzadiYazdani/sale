
package com.haraji.baseinfo.mapper.business;

import com.haraji.baseinfo.api.businessUnion.BusinessUnionRequest;
import com.haraji.baseinfo.api.businessUnion.BusinessUnionResponse;
import com.haraji.baseinfo.database.entity.business.BusinessUnionEntity;
import com.haraji.baseinfo.database.entity.location.CityEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface BusinessUnionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "city", source = "cityId")
    @Mapping(target = "businessCategoryEntities", ignore = true)
    BusinessUnionEntity toEntity(BusinessUnionRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "city", source = "cityId")
    @Mapping(target = "businessCategoryEntities", ignore = true)
    void updateEntity(
            BusinessUnionRequest request,
            @MappingTarget BusinessUnionEntity entity
    );

    @Mapping(target = "cityId", source = "city.id")
    BusinessUnionResponse toDtoResponse(BusinessUnionEntity entity);

    List<BusinessUnionResponse> toDtoResponseList(
            List<BusinessUnionEntity> entities
    );

    default CityEntity map(Long cityId) {
        if (cityId == null) {
            return null;
        }

        return CityEntity.newInstance()
                .setId(cityId)
                .build();
    }
}
