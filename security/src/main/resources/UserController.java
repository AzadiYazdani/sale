package com.haraji.security.api;

import com.haraji.common.dto.ResponseDto;
import com.haraji.security.api.dto.UserEditRequestDto;
import com.haraji.security.api.dto.register.UserResponseDto;
import com.haraji.security.mapper.UserMapper;
import com.haraji.security.model.User;
import com.haraji.security.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@RestController
@Tag(name = "User operations", description = "عملیات مربوط به کاربران")
@RequestMapping("/user")
@Slf4j
@Validated
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "یافتن همه کاربران")
    public ResponseEntity<ResponseDto<List<UserResponseDto>>> getAllUsers() {
        List<User> user = userService.getAll();
        List<UserResponseDto> lstDtoResponse = userMapper.toDtoResponseList(user);
        log.debug("the UserDto for sending is {}", lstDtoResponse);
        return new ResponseEntity<>(ResponseDto.success(lstDtoResponse), HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "یافتن یک کاربر با شناسه")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseDto<UserResponseDto>> getById(
            @PathVariable("userId") @Valid @Min(1) @Parameter(description = "شناسه کاربر", example = "1", required = true) int userId) {

        log.debug("getById received userId is {}", userId);
        User user = userService.getById((long) userId);
        UserResponseDto dtoResponse = userMapper.toDtoResponse(user);
        log.debug("the UserDto for sending is {}", dtoResponse);
        return new ResponseEntity<>(ResponseDto.success(dtoResponse), HttpStatus.OK);
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "ویرایش پروفایل کاربری")
    @PreAuthorize("hasAnyAuthority('VIEWER')")
    public ResponseEntity<ResponseDto<UserResponseDto>> editUser(
            @RequestBody @NonNull @Parameter(description = "ویرایش ویژگی های کاربر", required = true) UserEditRequestDto userEditRequestDto) {

        log.debug("editById received");
        User user = userService.editUser(userEditRequestDto);
        UserResponseDto dtoResponse = userMapper.toDtoResponse(user);
        log.debug("the UserDto for sending is {}", dtoResponse);
        return new ResponseEntity<>(ResponseDto.success(dtoResponse), HttpStatus.OK);
    }

    @PutMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "ویرایش یک کاربر با شناسه توسط ادمین")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseDto<UserResponseDto>> editByAdmin(
            @PathVariable("userId") @Valid @Min(1) @Parameter(description = "شناسه کاربر", example = "1", required = true) int userId,
            @RequestBody @NonNull @Parameter(description = "ویرایش ویژگی های کاربر", required = true) UserEditRequestDto userEditRequestDto) {

        log.debug("editById received userId is {}", userId);
        User user = userService.getById((long) userId);
        // نکته: اگر نیاز به ذخیره تغییرات دارید، متد سرویس برای ذخیره باید فراخوانی شود (در کد اصلی موجود نبود)
        UserResponseDto dtoResponse = userMapper.toDtoResponse(user);
        log.debug("the UserDto for sending is {}", dtoResponse);
        return new ResponseEntity<>(ResponseDto.success(dtoResponse), HttpStatus.OK);
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "یافتن همه کاربرانی که بخشی از یک واژه را دارند")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseDto<List<UserResponseDto>>> searchUsername(
            @RequestParam("title") @Valid @NotNull @Parameter(description = "بخشی از نام کاربر مورد نظر", example = "آذر", required = true) String title) {

        log.debug("received value for searching name is {}", title);
        List<User> userList = userService.searchUsername(title);
        List<UserResponseDto> userResponseDtoList = userMapper.toDtoResponseList(userList);
        log.debug("the list of users for sending is {}", userResponseDtoList);
        return new ResponseEntity<>(ResponseDto.success(userResponseDtoList), HttpStatus.OK);
    }
}
