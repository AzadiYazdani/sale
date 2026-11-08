
package com.haraji.security.dto.otp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(builderMethodName = "newInstance")
@Schema(
        name = "VerifyOtpResponse",
        description = "پاسخ تأیید موفق کد ثبت‌نام"
)
public class VerifyOtpResponse {

    @Schema(
            description = "شناسه کاربر ثبت‌نام‌شده",
            example = "125",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long userId;

    @Schema(
            description = "پیام نتیجه تأیید کد و تکمیل ثبت‌نام",
            example = "Registration completed successfully",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String message;
}
