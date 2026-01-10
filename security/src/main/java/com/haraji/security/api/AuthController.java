package com.haraji.security.api;

import com.haraji.security.api.dto.LoginRequestDto;
import com.haraji.security.api.dto.LoginResponse;
import com.haraji.security.api.dto.UserRequestDto;
import com.haraji.security.model.User;
import com.haraji.security.service.UserService;
import com.haraji.security.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/login")
    @ApiOperation(value = "دریافت توکن", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> login(@RequestBody @NonNull @ApiParam(value = "نام کاربری و گذواژه دامنه برای جستجو در DB", required = true) LoginRequestDto loginRequest) {
        log.info("get token");
        Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        String token = jwtUtil.generateToken(authentication.getName());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody UserRequestDto userRequestDto) {
        log.debug("received user request for creating a user is {}", userRequestDto);
        User user = userService.createUser(userRequestDto);
        String jwtToken = jwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(new LoginResponse(jwtToken));
    }
}
