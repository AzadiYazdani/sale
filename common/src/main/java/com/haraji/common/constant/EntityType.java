package com.haraji.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EntityType {

    USER("User", "کاربر"),
    CITY("City", "شهر"),
    PROVINCE("province", "استان"),
    ACTIVITY_TYPE("activity Type", "نوع کسب‌وکار"),
    BUSINESS_UNION("businessUnion union", "اتحادیه صنفی"),
    SALE("sale", "حراجی");

    private final String englishValue;
    private final String persianValue;

    public String getValue(Language language) {

        return switch (language) {
            case ENGLISH -> englishValue;
            case PERSIAN -> persianValue;
        };

    }

}
