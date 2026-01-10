package com.haraji.security.constant;

import java.util.Arrays;

public enum PersonType {
    
    REAL(0),
    LEGAL(1),
    ;

    private final Integer value;

    PersonType(Integer value) {
        this.value = value;
    }

    public static PersonType getByValue(Integer value) {
        return Arrays.stream(values())
                .filter(item -> item.value.equals(value))
                .findAny()
                .orElse(null);
    }

    public Integer getValue() {
        return value;
    }
}
