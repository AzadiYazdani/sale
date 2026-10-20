package com.haraji.baseinfo.mapper.business;


import com.haraji.baseinfo.api.activityType.ActivityTypeRequest;
import com.haraji.baseinfo.api.activityType.ActivityTypeResponse;
import com.haraji.baseinfo.database.entity.business.ActivityTypeEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ActivityTypeMapper {

    List<ActivityTypeResponse> toDtoResponseList(List<ActivityTypeEntity> entities);
    
    ActivityTypeResponse toDtoResponse(ActivityTypeEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "description", ignore = true)
    ActivityTypeEntity toEntity(ActivityTypeRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "description", ignore = true)
    void updateEntity(ActivityTypeRequest request, @MappingTarget ActivityTypeEntity entity);}
