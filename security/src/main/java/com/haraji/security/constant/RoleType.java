package com.haraji.security.constant;

public enum RoleType {
    ADMIN(1),
    REGISTER(2),
    SELLER(3),
    VIEWER(4);


    private final int value;

    RoleType(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }
}
