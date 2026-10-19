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
    private Long id;
    private String identifier;
    private RoleType role;
    private Boolean enabled;
    private LocalDateTime createTime;
    private PersonDto person;
}
