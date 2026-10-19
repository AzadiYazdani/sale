
package com.haraji.security.dto.token;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(
        name = "RefreshTokenRequest",
        description = "درخواست دریافت توکن‌های جدید با استفاده از توکن تمدید"
)
public class RefreshTokenRequest {

    @Schema(
            description = "توکن تمدید معتبر دریافت‌شده هنگام ورود",
            example = "a9f6e31b-1234-4567-890a-abcdef123456",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank
    private String refreshToken;
}
