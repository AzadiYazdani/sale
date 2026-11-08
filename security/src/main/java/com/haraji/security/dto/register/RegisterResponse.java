
package com.haraji.security.dto.register;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(builderMethodName = "newInstance")
@Schema(
        name = "RegisterResponse",
        description = "پاسخ ثبت‌نام اولیه کاربر"
)
public class RegisterResponse {

    @Schema(
            description = "شناسه کاربر ایجادشده",
            example = "125",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long userId;

    @Schema(
            description = "پیام نتیجه ثبت‌نام و ارسال کد تأیید",
            example = "Verification code has been sent.",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String message;
}
