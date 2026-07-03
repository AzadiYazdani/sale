package com.haraji.security.mapper;


import com.haraji.security.api.dto.register.UserRequestDto;
import com.haraji.security.api.dto.register.UserResponseDto;
import com.haraji.security.constant.RoleEnum;
import com.haraji.security.database.entity.RoleEntity;
import com.haraji.security.database.entity.UserEntity;
import com.haraji.security.model.User;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public abstract class UserMapper {

    public abstract UserEntity toEntity(UserRequestDto userRequestDto);

    public abstract User toModel(UserEntity entity);

    public abstract List<User> toModelList(List<UserEntity> businessTypeEntityList);

    public abstract List<UserResponseDto> toDtoResponseList(List<User> businessTypeList);

    public abstract UserResponseDto toDtoResponse(User userList);

    protected RoleEnum map(RoleEntity roleEntity) {
        return roleEntity == null ? null : roleEntity.getRole();
    }
}
