package com.haraji.security.api;

import com.haraji.common.dto.ApiResponse;
import com.haraji.security.api.dto.UserEditRequestDto;
import com.haraji.security.api.dto.register.UserResponseDto;
import com.haraji.security.mapper.UserMapper;
import com.haraji.security.model.User;
import com.haraji.security.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "یافتن همه کاربران")
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> getAllUsers() {

        List<User> users = userService.getAll();
        List<UserResponseDto> response = userMapper.toDtoResponseList(users);
        log.debug("sending {} users", response.size());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "یافتن یک کاربر با شناسه")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponseDto>> getById(@PathVariable("userId") @Min(1) @Parameter(  description = "شناسه کاربر", example = "1", required = true) Long userId) {
        log.debug("received userId={} for retrieving user", userId);
        User user = userService.getById(userId);
        UserResponseDto response = userMapper.toDtoResponse(user);
        log.debug("sending user={}", response);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "ویرایش پروفایل کاربری")
    @PreAuthorize("hasAnyAuthority('VIEWER')")
    public ResponseEntity<ApiResponse<UserResponseDto>> editUser(@RequestBody @Valid @Parameter( description = "ویژگی‌های کاربر برای ویرایش", required = true) UserEditRequestDto userEditRequestDto) {

        log.debug("received request for editing user profile");
        User user = userService.editUser(userEditRequestDto);
        UserResponseDto response = userMapper.toDtoResponse(user);
        log.debug("sending edited user={}", response);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "ویرایش یک کاربر با شناسه توسط ادمین")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponseDto>> editByAdmin(
            @PathVariable("userId") @Min(1) @Parameter( description = "شناسه کاربر", example = "1", required = true) Long userId,
            @RequestBody @Valid @Parameter(description = "ویژگی‌های کاربر برای ویرایش", required = true) UserEditRequestDto userEditRequestDto) {

        log.debug("received request for editing user with id={}", userId);
        User user = userService.editUserByAdmin(userId, userEditRequestDto);
        UserResponseDto response = userMapper.toDtoResponse(user);
        log.debug("sending edited user={}", response);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "یافتن همه کاربرانی که بخشی از نام آن‌ها با عبارت جستجو مطابقت دارد")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> searchUsername(
            @RequestParam("title") @NotBlank @Parameter( description = "بخشی از نام کاربر مورد نظر", example = "علی", required = true) String title) {

        log.debug("received title={} for searching users", title);
        List<User> users = userService.searchUsername(title);
        List<UserResponseDto> response = userMapper.toDtoResponseList(users);
        log.debug("sending {} users", response.size());
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
