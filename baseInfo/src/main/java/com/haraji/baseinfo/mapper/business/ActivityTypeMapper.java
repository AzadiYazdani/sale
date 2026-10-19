package com.haraji.baseinfo.mapper.business;


import com.haraji.baseinfo.api.activityType.ActivityTypeResponse;
import com.haraji.baseinfo.database.entity.business.ActivityTypeEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ActivityTypeMapper {

    List<ActivityTypeResponse> toDtoResponseList(List<ActivityTypeEntity> entities);
    
    ActivityTypeResponse toDtoResponse(ActivityTypeEntity entity);

}
