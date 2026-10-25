package com.haraji.security.dto.user;

import com.haraji.security.constant.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String identifier;
    private RoleType role;
    private Boolean enabled;
    private LocalDateTime createdDate;
    private PersonDto person;
}
