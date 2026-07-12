package com.haraji.security.api.dto.register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
public class RegisterResponse {

    private Long userId;

    private String message;

    /**
     * اگر true باشد کاربر باید ایمیل یا موبایل را تایید کند.
     */
    private Boolean verificationRequired;

}
