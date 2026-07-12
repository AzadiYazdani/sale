package com.haraji.security.api.dto.user;

import com.haraji.security.constant.RoleType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private Long id;

    private String identifier;

    private RoleType role;

    private Boolean enabled;

}
