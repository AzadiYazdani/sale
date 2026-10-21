package com.haraji.security.api;


import com.haraji.security.dto.login.LoginPasswordRequest;
import com.haraji.security.dto.otp.OtpRequest;
import com.haraji.security.dto.otp.VerifyOtpRequest;
import com.haraji.security.dto.otp.VerifyOtpResponse;
import com.haraji.security.dto.register.RegisterRequest;
import com.haraji.security.dto.register.RegisterResponse;
import com.haraji.security.dto.token.RefreshTokenRequest;
import com.haraji.security.dto.token.TokenResponse;
import com.haraji.security.service.user.LoginService;
import com.haraji.security.service.user.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(
        name = "Authentication",
        description = "ثبت‌نام، ورود و مدیریت توکن‌های احراز هویت"
)
public class AuthController {

    private final LoginService loginService;
    private final RegisterService registerService;

    @PostMapping("/register")
    @Operation(
            summary = "ثبت‌نام کاربر",
            description = "ثبت‌نام با ایمیل یا شماره تلفن و رمز عبور و ارسال کد تأیید از کانال فعال"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "ثبت‌نام اولیه انجام شد"),
            @ApiResponse(responseCode = "400", description = "اطلاعات ورودی نامعتبر است"),
            @ApiResponse(responseCode = "409", description = "ایمیل قبلاً ثبت شده است")
    })
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(registerService.register(request));
    }

    @PostMapping("/register/otp/verify")
    @Operation( summary = "تأیید ایمیل ثبت‌نام",description = "بررسی کد ارسال‌شده به ایمیل و تکمیل فعال‌سازی حساب"
    )
    @ApiResponses({  @ApiResponse(responseCode = "200", description = "ایمیل تأیید و حساب فعال شد"),
            @ApiResponse(responseCode = "400", description = "کد نامعتبر یا منقضی شده است")
    })
    public ResponseEntity<VerifyOtpResponse> verifyRegisterOtp( @Valid @RequestBody VerifyOtpRequest request) {
        return ResponseEntity.ok( registerService.verifyRegisterOtp(request));
    }

    @PostMapping("/login")
    @Operation(summary = "ورود با رمز عبور",description = "احراز هویت کاربر با ایمیل و رمز عبور")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "ورود موفق"),
            @ApiResponse(responseCode = "400", description = "اطلاعات ورود نامعتبر است"),
            @ApiResponse(responseCode = "401", description = "احراز هویت ناموفق است")
    })
    public ResponseEntity<TokenResponse> login( @Valid @RequestBody LoginPasswordRequest request) {
        return ResponseEntity.ok(loginService.loginPassword(request));
    }

    @PostMapping("/login/otp")
    @Operation( summary = "درخواست کد ورود", description = "ارسال کد یک‌بارمصرف ورود به ایمیل کاربر")
    @ApiResponses({ @ApiResponse(responseCode = "204", description = "درخواست پردازش شد"),
            @ApiResponse(responseCode = "400", description = "درخواست نامعتبر است")
    })
    public ResponseEntity<Void> requestLoginOtp( @Valid @RequestBody OtpRequest request) {
        loginService.loginOtp(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login/otp/verify")
    @Operation( summary = "ورود با کد یک‌بارمصرف", description = "تأیید کد ایمیلی و دریافت توکن‌های احراز هویت")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "ورود موفق"),
            @ApiResponse(responseCode = "400", description = "کد نامعتبر یا منقضی شده است")})
    public ResponseEntity<TokenResponse> verifyLoginOtp( @Valid @RequestBody VerifyOtpRequest request) {
        return ResponseEntity.ok(loginService.verifyLoginOtp(request));
    }

    @PostMapping("/token/refresh")
    @Operation(summary = "تمدید توکن‌ها",description = "دریافت توکن‌های جدید با استفاده از refresh token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "توکن‌ها تمدید شدند"),
            @ApiResponse(responseCode = "401", description = "توکن تمدید نامعتبر یا منقضی است")})
    public ResponseEntity<TokenResponse> refreshToken( @Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(loginService.refreshToken(request));
    }

    @PostMapping("/logout")
    @Operation( summary = "خروج از حساب",description = "باطل‌کردن refresh token ارائه‌شده")
    @ApiResponses({ @ApiResponse(responseCode = "204", description = "خروج انجام شد"),
            @ApiResponse(responseCode = "400", description = "توکن نامعتبر است")
    })
    public ResponseEntity<Void> logout( @Valid @RequestBody RefreshTokenRequest request) {
        loginService.logout(request.getRefreshToken());
        return ResponseEntity.noContent().build();
    }
}
