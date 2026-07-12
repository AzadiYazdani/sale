package com.haraji.security.model;

import com.haraji.security.constant.RoleType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
public class User {

    private Long id;
    private String username;
    private String password;
    private LocalDateTime createTime;
    private LocalDateTime expireTime;
    private RoleType role;
    private Person person;

}
