
package com.haraji.security.util;

import com.haraji.common.exception.InvalidFormatException;
import com.haraji.security.constant.IdentifierType;

import java.util.Locale;
import java.util.regex.Pattern;

public class CommonUtil {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^9[0-9]{9}$");

    public static String normalizeIdentifier(String rawValue, IdentifierType type) {
        if (type == null) {
            throw new InvalidFormatException("نوع شناسه الزامی است.");
        }

        if (rawValue == null || rawValue.isBlank()) {
            throw new InvalidFormatException("مقدار شناسه الزامی است.");
        }

        String value = rawValue.trim();

        return switch (type) {
            case USERNAME -> normalizeUsername(value);
            case EMAIL -> normalizeEmail(value);
            case PHONE -> normalizePhoneNumber(value);
        };
    }

    private static String normalizeUsername(String username) {
        if (username.length() > 100) {
            throw new InvalidFormatException("نام کاربری نمی‌تواند بیشتر از ۱۰۰ کاراکتر باشد.");
        }

        return username;
    }

    private static String normalizeEmail(String email) {
        String value = email.trim().toLowerCase(Locale.ROOT);

        if (value.length() > 254 || !EMAIL_PATTERN.matcher(value).matches()) {
            throw new InvalidFormatException("قالب ایمیل معتبر نیست.");
        }

        return value;
    }

    private static String normalizePhoneNumber(String phone) {
        String value = normalizePhoneInput(phone);

        String digits;

        if (value.startsWith("+98")) {
            digits = value.substring(3);
        } else if (value.startsWith("0098")) {
            digits = value.substring(4);
        } else if (value.startsWith("98") && value.length() == 12) {
            digits = value.substring(2);
        } else if (value.startsWith("09") && value.length() == 11) {
            digits = value.substring(1);
        } else {
            digits = value;
        }

        if (!PHONE_PATTERN.matcher(digits).matches()) {
            throw new InvalidFormatException("شماره موبایل ایران معتبر نیست.");
        }

        return "+98" + digits;
    }

    private static String normalizePhoneInput(String phone) {
        StringBuilder converted = new StringBuilder();

        for (char ch : phone.toCharArray()) {
            if (ch >= '۰' && ch <= '۹') {
                converted.append((char) ('0' + ch - '۰'));
            } else if (ch >= '٠' && ch <= '٩') {
                converted.append((char) ('0' + ch - '٠'));
            } else {
                converted.append(ch);
            }
        }

        String value = converted.toString().replaceAll("[\\s().-]", "");

        if (!value.matches("^\\+?[0-9]+$")) {
            throw new InvalidFormatException("قالب شماره موبایل معتبر نیست.");
        }

        return value;
    }

    public String maskIdentifier(String identifier) {
        if (identifier == null) {
            return "null";
        }

        if (identifier.length() <= 3) {
            return "***";
        }

        return identifier.substring(0, 3)
                + "***"
                + identifier.substring(identifier.length() - 2);
    }
}
