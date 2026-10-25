
package com.haraji.security.dto.login;

import com.haraji.security.constant.IdentifierType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "درخواست ورود با گذرواژه")
public class LoginPasswordRequest {

    @NotNull
    @Schema(description = "نوع شناسه", example = "USERNAME")
    private IdentifierType identifierType;

    @NotBlank
    @Schema(description = "نام کاربری", example = "azadi.yazdani")
    private String identifier;

    @NotBlank
    @Schema(description = "گذرواژه", example = "example@123")
    private String password;
}
