package com.haraji.security.api;

import com.haraji.common.dto.ApiResponse;
import com.haraji.common.logging.LogLevel;
import com.haraji.common.logging.MethodLogging;
import com.haraji.security.dto.user.UserEditRequest;
import com.haraji.security.dto.user.UserResponse;
import com.haraji.security.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "User operations", description = "عملیات مربوط به کاربران")
@Validated
@MethodLogging(level = LogLevel.DEBUG, logResult = false)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(params = "!username")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "یافتن همه کاربران به صورت صفحه‌بندی‌شده")
    public ResponseEntity<ApiResponse<Page<UserResponse>>> getAllUsers(@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<UserResponse> response = userService.getAllByPaging(pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "یافتن کاربر با شناسه")
    public ResponseEntity<ApiResponse<UserResponse>> getById(@PathVariable("userId") @Min(1) @Parameter(description = "شناسه کاربر", example = "1", required = true) Long userId) {
        UserResponse response = userService.getById(userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping(params = "username")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "جست‌وجوی کاربران با بخشی از شناسه ورود")
    public ResponseEntity<ApiResponse<List<UserResponse>>> searchUsername(@RequestParam("username") @NotBlank @Parameter(description = "بخشی از شناسه ورود", example = "ali", required = true) String username) {
        List<UserResponse> response = userService.searchUsername(username);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('VIEWER', 'ADMIN')")
    @Operation(summary = "دریافت پروفایل کاربر جاری")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser() {
        UserResponse response = userService.getCurrentUser();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/me")
    @PreAuthorize("hasAnyRole('VIEWER', 'ADMIN')")
    @Operation(summary = "تکمیل یا ویرایش پروفایل کاربر جاری")
    public ResponseEntity<ApiResponse<UserResponse>> editUser(@RequestBody @Valid UserEditRequest request) {
        UserResponse response = userService.editUser(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "ویرایش پروفایل کاربر توسط مدیر")
    public ResponseEntity<ApiResponse<UserResponse>> editByAdmin(@PathVariable("userId") @Min(1) @Parameter(description = "شناسه کاربر", example = "1", required = true) Long userId, @RequestBody @Valid UserEditRequest request) {
        UserResponse response = userService.editUserByAdmin(userId, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
