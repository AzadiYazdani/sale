package com.haraji.security.api.dto.token;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {

    private String accessToken;

    private String refreshToken;

    private Long expiresIn;

}
