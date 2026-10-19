package com.haraji.security.util;

import com.haraji.common.exception.InvalidFormatException;
import com.haraji.security.constant.IdentifierType;
import jakarta.annotation.Nonnull;

import java.util.Locale;
import java.util.regex.Pattern;

public class CommonUtil {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    public static IdentifierType detectLoginType(String identifier) {
        if (identifier == null || identifier.isBlank()) {
            throw new InvalidFormatException();
        }

        String value = identifier.trim();

        try {
            normalizeEmail(value);
            return IdentifierType.EMAIL;
        } catch (IllegalArgumentException ignored) {
            // شناسه ایمیل معتبر نیست؛ شماره تلفن را بررسی می‌کنیم.
        }

        try {
            normalizePhoneNumber(value);
            return IdentifierType.PHONE;
        } catch (IllegalArgumentException ignored) {
            throw new InvalidFormatException();
        }
    }

    public static String normalizeIdentifier(
            String rawValue,
            IdentifierType type) {

        if (type == null) {
            throw new IllegalArgumentException("نوع شناسه الزامی است.");
        }

        if (rawValue == null || rawValue.isBlank()) {
            throw new IllegalArgumentException("مقدار شناسه الزامی است.");
        }

        String value = rawValue.trim();

        return switch (type) {
            case EMAIL -> normalizeEmail(value);
            case PHONE -> normalizePhoneNumber(value);
        };
    }

    private static String normalizeEmail(String email) {
        String value = email.trim().toLowerCase(Locale.ROOT);

        if (value.length() > 254
                || !EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("قالب ایمیل معتبر نیست.");
        }

        return value;
    }

    private static String normalizePhoneNumber(String phone) {
        String value = getString(phone);

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

        if (!digits.matches("^9[0-9]{9}$")) {
            throw new IllegalArgumentException("شماره موبایل ایران معتبر نیست.");
        }

        return "+98" + digits;
    }

    @Nonnull
    private static String getString(String phone) {
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
            throw new IllegalArgumentException("قالب شماره موبایل معتبر نیست.");
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
