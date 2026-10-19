
package com.haraji.security.dto.otp;

import com.haraji.security.constant.IdentifierType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "درخواست ارسال کد ورود")
public class OtpRequest {

    @NotNull
    @Schema(description = "نوع شناسه", example = "EMAIL")
    private IdentifierType identifierType;

    @NotBlank
    @Schema(description = "ایمیل یا شماره تلفن", example = "user@example.com")
    private String identifier;
}
