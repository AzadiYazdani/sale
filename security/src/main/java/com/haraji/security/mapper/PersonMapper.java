package com.haraji.security.mapper;


import com.haraji.common.util.DateUtility;
import com.haraji.security.api.dto.UserRequestDto;
import com.haraji.security.api.dto.UserResponseDto;
import com.haraji.security.database.entity.PersonEntity;
import com.haraji.security.model.Person;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",  uses = {DateUtility.class},  builder = @Builder(disableBuilder = true))
public interface PersonMapper {

    Person toModel(PersonEntity entity);

    List<Person> toModelList(List<PersonEntity> entities);

    PersonEntity toEntity(UserRequestDto userRequestDto);

    List<UserResponseDto> toDtoResponseList(List<Person> businessTypeList);

    UserResponseDto toDtoResponse(Person userList);

}
