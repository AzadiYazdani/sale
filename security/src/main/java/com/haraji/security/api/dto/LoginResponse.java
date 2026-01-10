package com.haraji.security.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class LoginResponse {
	private static final long serialVersionUID = -5842662313715118663L;

	private String token;

	public LoginResponse(String token){
		this.token = token;
	}
}
