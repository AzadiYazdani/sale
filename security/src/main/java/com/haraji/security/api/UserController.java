package com.haraji.security.api;


import com.haraji.common.dto.ResponseDto;
import com.haraji.security.api.dto.UserResponseDto;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
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
    public ResponseEntity<ResponseDto<UserResponseDto>> getById(@PathVariable("userId") @Valid @Min(1) @ApiParam(value = "شناسه کاربر", example = "1", required = true) int userId) {
        log.debug("received userId for retrieving a user is {}", userId);
        User user = userService.getById(userId);
        UserResponseDto dtoResponse = userMapper.toDtoResponse(user);
        log.debug("the UserDto for sending is {}", dtoResponse);
        return new ResponseEntity<ResponseDto<UserResponseDto>>(ResponseDto.success(dtoResponse), HttpStatus.OK);
    }
}
