package com.haraji.security.api.dto.otp;

import com.haraji.security.constant.IdentifierType;
import com.haraji.security.constant.OtpPurpose;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VerifyOtpRequest {

    @NotBlank
    private String identifier;

    @NotNull
    private IdentifierType identifierType;

    @NotBlank
    private String code;

    @NotNull
    private OtpPurpose purpose;

}
