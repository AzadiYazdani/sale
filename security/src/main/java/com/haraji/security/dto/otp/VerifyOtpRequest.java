package com.haraji.security.dto.otp;

import com.haraji.security.constant.IdentifierType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Schema(description = "درخواست تأیید کد یک‌بارمصرف")
public class VerifyOtpRequest {

    @NotNull
    @Schema(description = "نوع شناسه", example = "EMAIL")
    private IdentifierType identifierType;

    @NotBlank
    @Schema(description = "ایمیل یا شماره تلفن", example = "user@example.com")
    private String identifier;

    @NotBlank
    @Pattern(regexp = "\\d{6}")
    @Schema(description = "کد تأیید شش‌رقمی", example = "123456")
    private String code;
}
