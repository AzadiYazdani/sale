package com.haraji.security.api.dto.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "مدل درخواست ورود به سیستم (لاگین)")
public class LoginRequestDto implements Serializable {
    private static final long serialVersionUID = 2583865165014770858L;

    @Schema(description = "نام کاربری", requiredMode = Schema.RequiredMode.REQUIRED, example = "azadi.yazdani")
    private String username;

    @Schema(description = "گذرواژه", requiredMode = Schema.RequiredMode.REQUIRED, example = "Idaza123")
    private String password;
}
