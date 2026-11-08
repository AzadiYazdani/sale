
package com.haraji.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EntityType implements LocalizedValue {

    USER("User", "کاربر"),
    CITY("City", "شهر"),
    PROVINCE("Province", "استان"),
    ACTIVITY_TYPE("Activity Type", "نوع کسب‌وکار"),
    BUSINESS_UNION("Business Union", "اتحادیه صنفی"),
    BUSINESS_CATEGORY("Business category", "رشته شغلی"),
    SALE("Sale", "حراجی");

    private final String englishValue;
    private final String persianValue;

    @Override
    public String getValue(Language language) {
        return switch (language) {
            case ENGLISH -> englishValue;
            case PERSIAN -> persianValue;
        };
    }
}
