package com.haraji.security.constant;

import com.haraji.common.constant.Language;

public enum IdentifierType {

    EMAIL("Email", "ایمیل"),

    PHONE("Phone Number", "شماره موبایل");

    private final String englishValue;
    private final String persianValue;

    IdentifierType(String englishValue, String persianValue) {
        this.englishValue = englishValue;
        this.persianValue = persianValue;
    }

    public String getValue(Language language) {
        return switch (language) {
            case ENGLISH -> englishValue;
            case PERSIAN -> persianValue;
        };
    }
}
