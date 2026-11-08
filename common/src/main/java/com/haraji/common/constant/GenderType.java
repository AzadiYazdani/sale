package com.haraji.common.constant;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum GenderType
{
    UNDEFINED(0),
    MALE(1),
    FEMALE(2),
            ;

    private final int value;

    GenderType(int value) {
        this.value = value;
    }

    public static GenderType getByValue(int input) {
        return Arrays.stream(values()).filter((person) -> {
            return (person.value == input);
        }).findAny().orElse(null);
    }

    public String getText() {
        return this.name();
    }

}
