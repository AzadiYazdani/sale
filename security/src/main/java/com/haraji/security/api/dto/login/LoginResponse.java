package com.haraji.security.api.dto.login;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
public class LoginResponse implements Serializable {
	@Serial
    private static final long serialVersionUID = -5842662313715118663L;

	private String accessToken;

	private String refreshToken;

	private String tokenType;
}
