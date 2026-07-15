package com.haraji.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EntityType {

    CITY("City", "شهر"),
    STATE("State", "استان"),
    BUSINESS_TYPE("Business Type", "نوع کسب‌وکار"),
    USER("User", "کاربر");

    private final String englishValue;
    private final String persianValue;

    public String getValue(Language language) {

        return switch (language) {
            case ENGLISH -> englishValue;
            case PERSIAN -> persianValue;
        };

    }

}
