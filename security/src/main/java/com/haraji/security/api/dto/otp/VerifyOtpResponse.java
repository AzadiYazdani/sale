package com.haraji.security.api.dto.otp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerifyOtpResponse {

    private Long userId;

    private String message;

}
