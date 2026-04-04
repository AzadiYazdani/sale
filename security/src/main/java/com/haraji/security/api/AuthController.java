package com.haraji.security.api;

import com.haraji.security.api.dto.login.LoginRequestDto;
import com.haraji.security.api.dto.login.LoginResponse;
import com.haraji.security.api.dto.register.UserRequestDto;
import com.haraji.security.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
@Api(value = "token operations")
@Slf4j
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    @ApiOperation(value = "دریافت توکن", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> login(@RequestBody @NonNull @ApiParam(value = "نام کاربری و گذواژه دامنه برای جستجو در DB", required = true) LoginRequestDto loginRequest) {
        log.debug("login request username: {}", loginRequest.getUsername() );
        String jwtToken = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(new LoginResponse(jwtToken));
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody @NonNull @ApiParam(value = "ویژگی های ثبت نامی کاربر", required = true)UserRequestDto userRequestDto) {
        log.debug("received user request for creating a user is {}", userRequestDto);
        String jwtToken = userService.createUser(userRequestDto);
        return ResponseEntity.ok(new LoginResponse(jwtToken));
    }
}
