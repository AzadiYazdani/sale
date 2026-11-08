
package com.haraji.security.dto.token;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(
        name = "TokenResponse",
        description = "پاسخ احراز هویت شامل توکن دسترسی و توکن تمدید"
)
public class TokenResponse {

    @Schema(
            description = "توکن دسترسی برای ارسال درخواست‌های احرازشده",
            example = "eyJhbGciOiJIUzI1NiJ9.example.access",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String accessToken;

    @Schema(
            description = "توکن تمدید برای دریافت توکن‌های جدید",
            example = "a9f6e31b-1234-4567-890a-abcdef123456",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String refreshToken;

    @Schema(
            description = "مدت اعتبار توکن دسترسی بر حسب میلی‌ثانیه",
            example = "86400000",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minimum = "1"
    )
    private Long expiresIn;
}
