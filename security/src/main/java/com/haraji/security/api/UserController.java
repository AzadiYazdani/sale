package com.haraji.security.api;


import com.haraji.common.dto.ResponseDto;
import com.haraji.security.api.dto.UserEditRequestDto;
import com.haraji.security.api.dto.register.UserResponseDto;
import com.haraji.security.mapper.UserMapper;
import com.haraji.security.model.User;
import com.haraji.security.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Api(value = "User operations")
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

    @GetMapping(value = "/all")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ApiOperation(value = "یافتن همه کاربران", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<UserResponseDto>> getAllUsers() {
        List<User> user = userService.getAll();
        List<UserResponseDto> lstDtoResponse = userMapper.toDtoResponseList(user);
        log.debug("the UserDto for sending is {}", lstDtoResponse);
        return new ResponseEntity<ResponseDto<UserResponseDto>>(ResponseDto.success(lstDtoResponse), HttpStatus.OK);
    }

//    @GetMapping("/paging")
//    @ApiOperation(value = "دریافت همه استانها با صفحه بندی", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ResponseDto<Page<UserResponseDto>>> getUsersWithPaging(@RequestParam @Valid @ApiParam(value = "شماره صفحه", example = "0", required = true) int pageNo, @RequestParam @Valid @ApiParam(value = "شمارگان اقلام در هر صفحه", example = "10", required = true) int pageSize, @ApiParam(value = "مرتب سازی بر اساس", example = "title") @RequestParam(required = false) String sortName, @ApiParam(value = "جهت مرتب سازی", example = "asc") @RequestParam(required = false) String asc) {
//        log.debug("received page number and size for retrieving all users are {}, {}", pageNo, pageSize);
//        String sortColumn = StringUtils.isNoneBlank(sortName) ? sortName : "title";
//        String direction = (StringUtils.isNoneBlank(asc) && (asc.equalsIgnoreCase("DESC") || asc.equalsIgnoreCase("asc"))) ? asc : "DESC";
//        Sort sort = Sort.by(Sort.Direction.valueOf(direction.toUpperCase()), sortColumn);
//        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
//        Page<User> userPage = userService.getAllByPaging(pageable);
//        log.debug("the userPage for sending is {}", userPage);
//        return new ResponseEntity<ResponseDto<Page<UserResponseDto>>>(ResponseDto.success(userPage), HttpStatus.OK);
//    }

    @GetMapping(value = "/{userId}")
    @ApiOperation(value = "یافتن یک کاربر با شناسه", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseDto<UserResponseDto>> getById(@PathVariable("userId") @Valid @Min(1) @ApiParam(value = "شناسه کاربر", example = "1", required = true) int userId) {
        log.debug("getById received userId is {}", userId);
        User user = userService.getById((long) userId);
        UserResponseDto dtoResponse = userMapper.toDtoResponse(user);
        log.debug("the UserDto for sending is {}", dtoResponse);
        return new ResponseEntity<ResponseDto<UserResponseDto>>(ResponseDto.success(dtoResponse), HttpStatus.OK);
    }

    @PutMapping(value = "")
    @ApiOperation(value = "ویرایش یک کاربر با شناسه", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('VIEWER')")
//    @Word.Operation(
//            summary = "دسترسی ادمین",
//            security = @SecurityRequirement(name = "bearerAuth")
//    )
    public ResponseEntity<ResponseDto<UserResponseDto>> editUser(@RequestBody @NonNull @ApiParam(value = "ویرایش ویژگی های کاربر", required = true) UserEditRequestDto userEditRequestDto) {
        log.debug("editById received");
        User user = userService.editUser(userEditRequestDto);
        UserResponseDto dtoResponse = userMapper.toDtoResponse(user);
        log.debug("the UserDto for sending is {}", dtoResponse);
        return new ResponseEntity<ResponseDto<UserResponseDto>>(ResponseDto.success(dtoResponse), HttpStatus.OK);
    }

    @PutMapping(value = "/{userId}")
    @ApiOperation(value = "ویرایش یک کاربر با شناسه", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseDto<UserResponseDto>> editByAdmin(@PathVariable("userId") @Valid @Min(1) @ApiParam(value = "شناسه کاربر", example = "1", required = true) int userId, @RequestBody @NonNull @ApiParam(value = "ویرایش ویژگی های کاربر", required = true) UserEditRequestDto userEditRequestDto) {
        log.debug("editById received userId is {}", userId);
        User user = userService.getById((long) userId);
        UserResponseDto dtoResponse = userMapper.toDtoResponse(user);
        log.debug("the UserDto for sending is {}", dtoResponse);
        return new ResponseEntity<ResponseDto<UserResponseDto>>(ResponseDto.success(dtoResponse), HttpStatus.OK);
    }

    @GetMapping("")
    @ApiOperation(value = "یافتن همه کاربرانی که بخشی از یک واژه را دارند", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseDto<List<UserResponseDto>>> searchUsername(@RequestParam("title") @Valid @NotNull @ApiParam(value = "بخشی از نام کاربر مورد نظر", example = "آذر", required = true) String title) {
        log.debug("received value for searching name is {}", title);
        List<User> userList = userService.searchUsername(title);
        List<UserResponseDto> businessTypeResponseDtoList = userMapper.toDtoResponseList(userList);
        log.debug("the list of businessType for sending is {}", businessTypeResponseDtoList);
        return new ResponseEntity<ResponseDto<List<UserResponseDto>>>(ResponseDto.success(businessTypeResponseDtoList), HttpStatus.OK);
    }
}
