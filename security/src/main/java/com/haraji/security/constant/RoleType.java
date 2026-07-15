package com.haraji.security.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {
    ADMIN(1),
    REGISTER(2),
    SELLER(3),
    VIEWER(4);


    private final int value;

}
