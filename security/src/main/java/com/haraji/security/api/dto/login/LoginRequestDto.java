package com.haraji.security.api.dto.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto implements Serializable {
    private static final long serialVersionUID = 2583865165014770858L;

    @ApiModelProperty(value = "نام کاربری", dataType = "String", required = true, example = "azadi.yazdani")
    private String username;

    @ApiModelProperty(value = "گذرواژه", dataType = "String", required = true, example = "Idaza123")
    private String password;
}
