package com.haraji.security.api.dto.otp;

import com.haraji.security.constant.IdentifierType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpRequest {

    private IdentifierType identifierType;

    private String identifier;

}
