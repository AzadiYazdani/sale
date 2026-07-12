package com.haraji.security.api.dto.register;

import com.haraji.security.constant.IdentifierType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotNull
    private IdentifierType identifierType;

    @NotBlank
    private String identifier;

    @NotBlank
    private String password;

}
