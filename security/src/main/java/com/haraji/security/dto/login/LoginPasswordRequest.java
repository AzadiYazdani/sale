
package com.haraji.security.dto.login;

import com.haraji.security.constant.IdentifierType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "درخواست ورود با رمز عبور")
public class LoginPasswordRequest {

    @NotNull
    @Schema(description = "نوع شناسه", example = "EMAIL")
    private IdentifierType identifierType;

    @NotBlank
    @Schema(description = "ایمیل یا شماره تلفن", example = "user@example.com")
    private String identifier;

    @NotBlank
    @Schema(description = "رمز عبور")
    private String password;
}
