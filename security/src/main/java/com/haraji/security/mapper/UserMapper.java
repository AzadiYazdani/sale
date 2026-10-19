
package com.haraji.security.mapper;

import com.haraji.security.database.entity.UserEntity;
import com.haraji.security.database.entity.UserIdentifierEntity;
import com.haraji.security.dto.user.UserResponse;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface UserMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "identifier", expression = "java(getPrimaryIdentifier(entity))")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "enabled", source = "enabled")
    @Mapping(target = "createTime", source = "createdDate")
    @Mapping(target = "person", source = "person")
    UserResponse toUserResponse(UserEntity entity);

    List<UserResponse> toUserResponseList(List<UserEntity> entities);

    default String getPrimaryIdentifier(UserEntity entity) {
        if (entity == null || entity.getIdentifiers() == null) {
            return null;
        }

        return entity.getIdentifiers().stream()
                .filter(identifier -> Boolean.TRUE.equals(identifier.getPrimaryIdentifier()) && Boolean.TRUE.equals(identifier.getEnabled()))
                .map(UserIdentifierEntity::getValue)
                .findFirst()
                .orElse(null);
    }
}
