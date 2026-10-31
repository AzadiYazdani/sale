
package com.haraji.security.mapper;

import com.haraji.common.util.DateUtil;
import com.haraji.common.util.JalaliCalendar;
import com.haraji.security.database.entity.PersonEntity;
import com.haraji.security.database.entity.UserEntity;
import com.haraji.security.database.entity.UserIdentifierEntity;
import com.haraji.security.dto.user.AdminUserResponse;
import com.haraji.security.dto.user.PersonDto;
import com.haraji.security.dto.user.UserResponse;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface UserMapper {

    @Mapping(target = "identifier", expression = "java(getPrimaryIdentifier(entity))")
    @Mapping(target = "createdDate", source = "createdDate", qualifiedByName = "toJalaliDateTime")
    UserResponse toUserResponse(UserEntity entity);

    @Mapping(target = "identifier", expression = "java(getPrimaryIdentifier(user))")
    @Mapping(target = "createdDate", source = "createdDate", qualifiedByName = "toJalaliDateTime")
    AdminUserResponse toAdminUserResponse(UserEntity user);

    @Mapping(target = "birthDate", source = "birthDate", qualifiedByName = "toJalaliDate")
    PersonDto toPersonDto(PersonEntity entity);

    default String getPrimaryIdentifier(UserEntity entity) {
        if (entity == null || entity.getIdentifiers() == null) return null;

        return entity.getIdentifiers().stream()
                .filter(identifier -> Boolean.TRUE.equals(identifier.getPrimaryIdentifier()) && Boolean.TRUE.equals(identifier.getEnabled()))
                .map(UserIdentifierEntity::getValue)
                .findFirst()
                .orElse(null);
    }

    @Named("toJalaliDateTime")
    default String toJalaliDateTime(LocalDateTime dateTime) {
        return DateUtil.convertToJalaliDateTime(dateTime);
    }

    @Named("toJalaliDate")
    default String toJalaliDate(LocalDate date) {
        if (date == null) return null;

        JalaliCalendar.YearMonthDate jalaliDate = DateUtil.convertToJalaliYearMonthDate(date);

        return String.format("%04d/%02d/%02d",
                jalaliDate.getYear(), jalaliDate.getMonth(), jalaliDate.getDay());
    }
}
