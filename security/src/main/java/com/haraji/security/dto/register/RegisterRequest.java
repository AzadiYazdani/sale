package com.haraji.security.dto.register;

import com.haraji.security.constant.IdentifierType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "درخواست ثبت‌ نام کاربر با ایمیل")
public class RegisterRequest {

    @Schema(
            description = "نوع نام کاربری؛ ایمیل یا موبایل",
            example = "EMAIL",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull
    private IdentifierType identifierType;

    @Schema(
            description = "آدرس ایمیل یا موبایل کاربر؛ شناسه اصلی ورود",
            example = "user@example.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )

    @Size(max = 254)
    private String identifier;


    @Schema(
            description = "گذرواژه کاربر",
            example = "Example@123",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 8, maxLength = 72
    )
    @NotBlank
    @Size(min = 8, max = 72)
    private String password;
}
