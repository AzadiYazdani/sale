package com.haraji.security.api.dto.login;

import com.haraji.security.constant.IdentifierType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "مدل درخواست ورود به سیستم (لاگین)")
public class LoginPasswordRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 2583865165014770858L;

    @Schema(description = "نام کاربری", requiredMode = Schema.RequiredMode.REQUIRED, example = "azadi.yazdani")
    @NotBlank
    private String identifier;

    @NotNull
    private IdentifierType identifierType;

    @Schema(description = "گذرواژه", requiredMode = Schema.RequiredMode.REQUIRED, example = "Idaza123")
    @NotBlank
    private String password;
}
